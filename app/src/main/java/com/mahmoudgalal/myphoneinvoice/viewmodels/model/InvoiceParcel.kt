package com.mahmoudgalal.myphoneinvoice.viewmodels.model

import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice
import java.util.ArrayList

class InvoiceParcel : Parcelable {
    var id: String? = null
    var totalAmount: String? = null
    var consumptionStart: String? = null
    var consumptionEnd: String? = null
    var subscribtionStart: String? = null
    var subscribtionEnd: String? = null
    var paymentGateNameAr: String? = null
    var detail_Taxes: String? = null
    var detail_Stamps: String? = null
    var detail_Features: String? = null
    var detail_ExtraCalls: String? = null
    var detail_Subsc: String? = null
    var detail_Delays: String? = null
    var detail_National: String? = null
    var detailsList: List<Invoice.Detail>? = null
    var status = 0

    constructor() {}
    private constructor(`in`: Parcel) {
        id = `in`.readString()
        totalAmount = `in`.readString()
        consumptionStart = `in`.readString()
        consumptionEnd = `in`.readString()
        subscribtionStart = `in`.readString()
        subscribtionEnd = `in`.readString()
        paymentGateNameAr = `in`.readString()
        detail_Taxes = `in`.readString()
        detail_Stamps = `in`.readString()
        detail_Features = `in`.readString()
        detail_ExtraCalls = `in`.readString()
        detail_Subsc = `in`.readString()
        detail_Delays = `in`.readString()
        detail_National = `in`.readString()
        `in`.readList(detailsList!!, null)
        status = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, i: Int) {
        out.writeString(id)
        out.writeString(totalAmount)
        out.writeString(consumptionStart)
        out.writeString(consumptionEnd)
        out.writeString(subscribtionStart)
        out.writeString(subscribtionEnd)
        out.writeString(paymentGateNameAr)
        out.writeString(detail_Taxes)
        out.writeString(detail_Stamps)
        out.writeString(detail_Features)
        out.writeString(detail_ExtraCalls)
        out.writeString(detail_Subsc)
        out.writeString(detail_Delays)
        out.writeString(detail_National)
        if (detailsList == null) detailsList = ArrayList()
        out.writeList(detailsList)
        out.writeInt(status)
    }

    companion object {
        @JvmField
        val CREATOR: Creator<InvoiceParcel> = object : Creator<InvoiceParcel> {
            override fun createFromParcel(`in`: Parcel): InvoiceParcel {
                return InvoiceParcel(`in`)
            }

            override fun newArray(size: Int): Array<InvoiceParcel?> {
                return arrayOfNulls(size)
            }
        }

        @JvmStatic
        fun createFromInvoice(invoice: Invoice): InvoiceParcel {
            val parcel = InvoiceParcel()
            parcel.status = invoice.status
            parcel.totalAmount = invoice.totalAmount.toString() + ""
            parcel.id = invoice.id
            parcel.paymentGateNameAr = invoice.paymentGateNameAr
            parcel.consumptionStart = invoice.consumptionStart.toString()
            parcel.consumptionEnd = invoice.consumptionEnd.toString()
            parcel.subscribtionStart = invoice.subscriptionStart.toString()
            parcel.subscribtionEnd = invoice.subscriptionEnd.toString()
            parcel.detailsList = invoice.details
            //        if(invoice.details != null && !invoice.details.isEmpty()){
//            for (ServiceResponse.Account.Invoice.Detail detail :invoice.details){
//                switch (detail.name){
//                    case "Subscription":
//                        parcel.detail_Subsc = detail.value+"";
//                        break;
//                    case "Extra Calls":
//                        parcel.detail_ExtraCalls = detail.value+"";
//                        break;
//                    case "Stamps":
//                        parcel.detail_Stamps = detail.value+"";
//                        break;
//                    case "Features":
//                        parcel.detail_Features = detail.value+"";
//                        break;
//                    case "Delays":
//                        parcel.detail_Delays = detail.value+"";
//                        break;
//                    case "Tax":
//                        parcel.detail_Taxes = detail.value+"";
//                        break;
//                    case "National":
//                        parcel.detail_National = detail.value+"";
//                        break;
//                    case "International":
//                        //parcel.detail_National = detail.value+"";
//                        break;
//                    case "Mobile":
//                        //parcel.detail_National = detail.value+"";
//                        break;
//                }
//            }
//        }
            return parcel
        }
    }
}