package com.mahmoudgalal.myphoneinvoice.fragments

import android.Manifest
import com.mahmoudgalal.myphoneinvoice.Utils.isExternalStorageWritable
import com.mahmoudgalal.myphoneinvoice.Utils.isInternetConnectionExist
import com.mahmoudgalal.myphoneinvoice.viewmodels.model.InvoiceParcel.Companion.createFromInvoice
import com.mahmoudgalal.myphoneinvoice.Utils.getPublicAppDocsDir
import com.mahmoudgalal.myphoneinvoice.adapters.InvoicesAdapter.OnItemClicked
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar
import android.widget.TextView
import com.mahmoudgalal.myphoneinvoice.adapters.InvoicesAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.mahmoudgalal.myphoneinvoice.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import android.widget.Toast
import android.content.Context
import android.media.MediaScannerConnection
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice
import com.mahmoudgalal.myphoneinvoice.viewmodels.InvoicesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class InvoicesFragment: Fragment(), OnItemClicked {
    private lateinit var invoicesRecycler: RecyclerView
    private var areaCode: String? = null
    private var phoneNumber: String? = null
    private lateinit var loadProgress: ProgressBar
    private lateinit var exportBtn: Button
    private lateinit var msgTxt: TextView
    private lateinit var adapter: InvoicesAdapter
    private val items: MutableList<Invoice> = ArrayList()
    private var fragmentMode = MODE_ONLY_REQUESTED
    private val MY_PERMISSIONS_REQUEST = 20101
    private val invoicesViewModel: InvoicesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoices, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        process()
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        invoicesRecycler = root.findViewById(R.id.invoices_recycler_view)
        msgTxt = root.findViewById(R.id.msgTxt)
        loadProgress = root.findViewById(R.id.progressBar)
        exportBtn = root.findViewById(R.id.export_btn)
        exportBtn.setEnabled(false)
        adapter = InvoicesAdapter(items)
        adapter.setOnItemClickedListener(this)
        val llm = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        invoicesRecycler.setLayoutManager(llm)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            llm.orientation
        )
        invoicesRecycler.addItemDecoration(dividerItemDecoration)
        invoicesRecycler.setHasFixedSize(true)
        invoicesRecycler.setAdapter(adapter)

        areaCode = requireArguments().getString(AREA_CODE_KEY)
        phoneNumber = requireArguments().getString(PHONE_NUMBER_KEY)
        fragmentMode = requireArguments().getInt(MODE_KEY, MODE_ONLY_REQUESTED)
        exportBtn.setOnClickListener(View.OnClickListener {
            if (!checkStoragePermissionAndRequest()) return@OnClickListener
            if (!isExternalStorageWritable) {
                Toast.makeText(context, "كارت الميمورى غير مقروء", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            val path = getPublicAppDocsDir("فواتير التليفون الارضي").absolutePath
            invoicesViewModel.exportAllInvoicesToXLSX(path)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun process() {
        invoicesViewModel.items.observe(viewLifecycleOwner){
            if (!isAdded || isDetached ) return@observe
            loadProgress.visibility = View.INVISIBLE
            items.clear()
            items += it
            adapter.notifyDataSetChanged()
            msgTxt.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            exportBtn.isEnabled = it.isNotEmpty()
        }
        invoicesViewModel.error.observe(viewLifecycleOwner){
            if (!isAdded || isDetached) return@observe
            loadProgress.visibility = View.INVISIBLE
            Log.d(TAG, "Error fetching data: $it")
            Toast.makeText(
                context, " خطا في سحب البيانات من السيرفر برجاء المحاوله مره اخري ! ",
                Toast.LENGTH_LONG
            ).show()
        }
        invoicesViewModel.exportState.observe(viewLifecycleOwner){
            when(it){
                InvoicesViewModel.ExportState.Started->
                    loadProgress.visibility = View.VISIBLE
                InvoicesViewModel.ExportState.Finished->
                    loadProgress.visibility = View.INVISIBLE
                InvoicesViewModel.ExportState.Succeeded->{
                    val path = getPublicAppDocsDir("فواتير التليفون الارضي").absolutePath
                    MediaScannerConnection.scanFile(
                        context, arrayOf(path+File.separator+"Invoices.xlsx"), null
                    ) { path, uri ->
                        Log.i(TAG, "Scanned $path:")
                        Log.i(TAG, "-> uri=$uri")
                    }
                        showAlert(
                            "تمت بنجاح",
                            """تم التخزين بنجاح فى المسار :
                                Documents/فواتير التليفون الارضي/Invoices.xlsx"""
                        )
                        Toast.makeText(context, "تم التخزين", Toast.LENGTH_LONG).show()
                }
                InvoicesViewModel.ExportState.Failed->
                        showAlert("خطأ", "خطأ اثناء التخزين")
            }
        }
        if (fragmentMode == MODE_ALL) {
            loadProgress.visibility = View.VISIBLE
            invoicesViewModel.loadAllInvoices()
        } else {
            if (isInternetConnectionExist(requireContext())) {
                loadProgress.visibility = View.VISIBLE
                invoicesViewModel.fetchPhoneInvoices(areaCode!!, phoneNumber!!)
            } else {
                Toast.makeText(
                    context, getString(R.string.no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onItemClicked(v: View?, position: Int, item: Invoice) {
        showInvoiceDetails(item)
    }

    private fun showAlert(title: String?, message: String?) {
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setMessage(message).setTitle(title)
            .setPositiveButton("Ok") { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }

    private fun showInvoiceDetails(invoice: Invoice) {
        val fragment = InvoiceDetailsFragment()
        val parcel = createFromInvoice(invoice)
        val args = Bundle()
        args.putParcelable(InvoiceDetailsFragment.LOADED_VALUE_KEY, parcel)
        fragment.arguments = args
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container, fragment,
                InvoiceDetailsFragment::class.java.simpleName
            )
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exportBtn.performClick()
            }
        }
    }

    private fun checkStoragePermissionAndRequest(): Boolean {
        // Here, thisActivity is the current activity
        return if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                EXTERNAL_PERMS[0]
            ) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(EXTERNAL_PERMS[0])) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(
                    this.context,
                    "يرجى تفهم أنك تحتاج إلى منح التطبيق إذن القراءة / الكتابة للتطبيق حتى تتمكن من استخدامه" +
                            ". انتقل إلى إعدادات الجهاز ونشط إذن التخزين للتطبيق.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // No explanation needed; request the permission
                requestPermissions(
                    EXTERNAL_PERMS,
                    MY_PERMISSIONS_REQUEST
                )
            }
            false
        } else {
            // Permission has already been granted
            true
        }
    }

    companion object {
        private val TAG = InvoicesFragment::class.java.simpleName
        const val AREA_CODE_KEY = "com.mahmoudgalal.myphoneinvoice.InvoicesFragment_AREA_CODE_KEY"
        const val PHONE_NUMBER_KEY =
            "com.mahmoudgalal.myphoneinvoice.InvoicesFragment_PHONE_NUMBER_KEY"
        const val MODE_KEY = "com.mahmoudgalal.myphoneinvoice.InvoicesFragment_MODE_KEY"
        const val MODE_ALL = 0
        const val MODE_ONLY_REQUESTED = 1
        val EXTERNAL_PERMS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        private fun isStoragePermissionGranted(context: Context): Boolean {
            return (ContextCompat.checkSelfPermission(
                context,
                EXTERNAL_PERMS[0]
            ) == PackageManager.PERMISSION_GRANTED)
        }

    }
}