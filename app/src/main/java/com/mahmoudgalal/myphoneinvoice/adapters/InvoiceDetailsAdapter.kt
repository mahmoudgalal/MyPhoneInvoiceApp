package com.mahmoudgalal.myphoneinvoice.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.mahmoudgalal.myphoneinvoice.R
import android.widget.TextView
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice

class InvoiceDetailsAdapter(private val items: List<Invoice.Detail>?) :
    RecyclerView.Adapter<InvoiceDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.invoice_detail_item, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]
        holder.setNameField(item.arabicName?:"")
        holder.setValueField("${item.value}")
    }

    override fun getItemCount()=items?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameField: TextView
        private val valueField: TextView

        fun setNameField(name: String) {
            nameField.text = name
        }

        fun setValueField(value: String) {
            valueField.text = value
        }

        init {
            nameField = itemView.findViewById(R.id.detail_txt)
            valueField = itemView.findViewById(R.id.detail_val)
        }
    }
}