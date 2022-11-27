package com.mahmoudgalal.myphoneinvoice.network.model

import com.google.gson.annotations.SerializedName
import androidx.room.PrimaryKey
import androidx.room.Embedded
import androidx.room.Entity

class ServiceResponse {
    @JvmField
    @SerializedName("Account")
    var account: Account? = null

    class Account {
        @SerializedName("Customer")
        val customer: Customer? = null

        @SerializedName("Invoices")
        val invoices: List<Invoice>? = null

        @SerializedName("UnPaidInvoices")
        val unPaidInvoices: List<Invoice>? = null

        class Customer {
            @SerializedName("AreaCode")
            var areaCode: String? = null

            @SerializedName("PhoneNumber")
            var phoneNumber: String? = null

            //@SerializedName("Account")
            //public String account;
            @SerializedName("CategoryName")
            var categoryName: String? = null
        }

        @Entity(tableName = "Invoices")
        data class Invoice(

            @PrimaryKey
            @SerializedName("ID")
            val id: String){

            @SerializedName("AreaCode")
            var areaCode: String? = null

            @SerializedName("PhoneNumber")
            var phoneNumber: String? = null

            @SerializedName("TotalAmount")
            var totalAmount = 0.0

            @Embedded(prefix = "CS_")
            @SerializedName("ConsumptionStart")
            var consumptionStart: DateStruct? = null

            @Embedded(prefix = "CE_")
            @SerializedName("ConsumptionEnd")
            var consumptionEnd: DateStruct? = null

            @Embedded(prefix = "SS_")
            @SerializedName("SubscribtionStart")
            var subscribtionStart: DateStruct? = null

            @Embedded(prefix = "SE_")
            @SerializedName("SubscribtionEnd")
            var subscribtionEnd: DateStruct? = null

            // public int Status
            @SerializedName("PaymentGateNameAr")
            var paymentGateNameAr: String? = null

            @SerializedName("Status") // @Ignore
            var status = 0

            @SerializedName("InvoiceDetails")
            var details: List<Detail>? = null

            class DateStruct {
                var Sec = 0
                var Min = 0
                var Hour = 0
                var Day = 0
                var Month = 0
                var Year = 0
                override fun toString(): String {
                    return Day.toString() + "/" + (Month + 1) + "/" + Year
                }
            }

            class Detail {
                @SerializedName("Name")
                var name: String? = null

                @SerializedName("Value")
                var value = 0.0

                @SerializedName("ArabicName")
                var arabicName: String? = null
            }
        }
    }
}