package com.mahmoudgalal.myphoneinvoice.adapters

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import com.mahmoudgalal.myphoneinvoice.R
import android.widget.TextView
import android.text.TextUtils
import android.view.View
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice

class InvoicesAdapter(private val items: List<Invoice>?) :
    RecyclerView.Adapter<InvoicesAdapter.ViewHolder>() {
    fun setOnItemClickedListener(onItemClickedListener: OnItemClicked?) {
        this.onItemClickedListener = onItemClickedListener
    }

    private var onItemClickedListener: OnItemClicked? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val root =
            LayoutInflater.from(parent.context).inflate(R.layout.invoice_item, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]
        val status = item.status
        holder.setTotal("${item.totalAmount}")
        holder.setPhoneNum(item.areaCode + "-" + item.phoneNumber)
        if (status > 0) { //paid
            holder.setPayedAt(item.paymentGateNameAr)
            holder.itemView.setBackgroundColor(Color.rgb(0x0, 0x0, 0xAA))
        } else {
            holder.setPayedAt("غير مدفوعه")
            holder.itemView.setBackgroundColor(Color.rgb(0xAA, 0x0, 0x0))
        }
        if (onItemClickedListener != null) {
            holder.itemView.setOnClickListener { view ->
                onItemClickedListener!!.onItemClicked(
                    view,
                    position,
                    item
                )
            }
        }
    }

    override fun getItemCount() = items?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val totalTxt: TextView
        private val payedAtTxt: TextView
        private val phoneNumTxt: TextView

        fun setTotal(total: String?) {
            totalTxt.text = total
        }

        fun setPayedAt(payedAt: String?) {
            payedAtTxt.text = payedAt
        }

        fun setPhoneNum(num: String?) {
            phoneNumTxt.text = num
        }

        init {
            totalTxt = itemView.findViewById(R.id.total_txt)
            payedAtTxt = itemView.findViewById(R.id.payed_at_txt)
            phoneNumTxt = itemView.findViewById(R.id.phone_num_txt)
            payedAtTxt.isSelected = true
            payedAtTxt.ellipsize = TextUtils.TruncateAt.MARQUEE
        }
    }

    interface OnItemClicked {
        fun onItemClicked(v: View?, position: Int, item: Invoice)
    }
}