package com.mahmoudgalal.myphoneinvoice.fragments

import android.widget.EditText
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import com.mahmoudgalal.myphoneinvoice.R
import android.widget.Toast
import android.text.TextUtils
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mahmoudgalal.myphoneinvoice.Utils
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class StartFragment : Fragment(), View.OnClickListener {
    private lateinit var checkBtn: Button
    private lateinit var showAllBtn: Button
    private lateinit var areaTxt: EditText
    private lateinit var phoneTxt: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_start, container, false)
        areaTxt = root.findViewById(R.id.area_code_txt)
        phoneTxt = root.findViewById(R.id.phone_txt)
        checkBtn = root.findViewById(R.id.check_btn)
        showAllBtn = root.findViewById(R.id.show_all)
        showAllBtn.setOnClickListener(this)
        checkBtn.setOnClickListener(this)
        return root
    }

    override fun onClick(view: View) {
        if (view === showAllBtn) {
            val args = Bundle()
            args.putInt(InvoicesFragment.MODE_KEY, InvoicesFragment.MODE_ALL)
            val fragment = InvoicesFragment()
            fragment.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment, InvoicesFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
            return
        }
        if (!Utils.isInternetConnectionExist(requireContext())) {
            Toast.makeText(
                context, R.string.no_internet_connection, Toast.LENGTH_LONG
            ).show()
            return
        }
        val areaCode = areaTxt.text.toString().trim()
        val phone = phoneTxt.text.toString().trim()
        if (areaCode.isNotEmpty() && phone.isNotEmpty() && TextUtils.isDigitsOnly(areaCode) &&
            TextUtils.isDigitsOnly(phone)
        ) {
            val args = Bundle()
            args.putString(InvoicesFragment.AREA_CODE_KEY, areaCode)
            args.putString(InvoicesFragment.PHONE_NUMBER_KEY, phone)
            val fragment = InvoicesFragment()
            fragment.arguments = args
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment, InvoicesFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        } else {
            Toast.makeText(context, "دخل رقم التليفون وكود المحافظه بشكل سليم !", Toast.LENGTH_LONG)
                .show()
        }
    }
}