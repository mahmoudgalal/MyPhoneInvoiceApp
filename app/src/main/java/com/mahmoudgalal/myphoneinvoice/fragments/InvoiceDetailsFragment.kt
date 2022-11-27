package com.mahmoudgalal.myphoneinvoice.fragments

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.mahmoudgalal.myphoneinvoice.adapters.InvoiceDetailsAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mahmoudgalal.myphoneinvoice.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.mahmoudgalal.myphoneinvoice.viewmodels.model.InvoiceParcel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class InvoiceDetailsFragment : Fragment() {
    private lateinit var invoiceTotal: TextView
    private lateinit var subFrom: TextView
    private lateinit var subTo: TextView
    private lateinit var consumFrom: TextView
    private lateinit var consumTo: TextView
    private lateinit var detailsRecycler: RecyclerView
    private lateinit var adapter: InvoiceDetailsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice_details, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        invoiceTotal = root.findViewById(R.id.invoice_value)
        subFrom = root.findViewById(R.id.sub_from)
        subTo = root.findViewById(R.id.sub_to)
        consumFrom = root.findViewById(R.id.consum_from)
        consumTo = root.findViewById(R.id.consum_to)
        detailsRecycler = root.findViewById(R.id.invoice_details_recycler_view)
        val llm = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        detailsRecycler.setLayoutManager(llm)
        val dividerItemDecoration = DividerItemDecoration(
            context,
            llm.orientation
        )
        detailsRecycler.addItemDecoration(dividerItemDecoration)
        detailsRecycler.setHasFixedSize(true)
        init()
    }

    private fun init() {
        val parcel = requireArguments().getParcelable<InvoiceParcel>(LOADED_VALUE_KEY)
        adapter = InvoiceDetailsAdapter(parcel!!.detailsList)
        detailsRecycler.adapter = adapter
        invoiceTotal.text = parcel.totalAmount
        subFrom.text = parcel.subscribtionStart
        subTo.text = parcel.subscribtionEnd
        consumFrom.text = parcel.consumptionStart
        consumTo.text = parcel.consumptionEnd
        //a nasty trick to get rid of the exception::
        //java.lang.RuntimeException: Parcel: unable to marshal value
        // com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse$Account$Invoice$Detail
        requireArguments().remove(LOADED_VALUE_KEY)
    }

    companion object {
        const val LOADED_VALUE_KEY = "com.mahmoudgalal.myphoneinvoice." +
                "fragments.InvoiceDetailsFragment_LOADED_VALUE_KEY"
    }
}