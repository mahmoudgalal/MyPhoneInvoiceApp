package com.mahmoudgalal.myphoneinvoice.data.model

data class Invoice(val id: String,
                   val areaCode: String? = null,
                   val phoneNumber: String? = null,
                   val totalAmount:Double = 0.0,
                   val consumptionStart: DateStruct? = null,
                   val consumptionEnd: DateStruct? = null,
                   val subscriptionStart: DateStruct? = null,
                   val subscriptionEnd: DateStruct? = null,
                   val paymentGateNameAr: String? = null,
                   val status:Int = 0,
                   var details: List<Detail>? = null){
    data class DateStruct(
        val Sec:Int = 0,
        val Min:Int = 0,
        val Hour:Int = 0,
        val Day:Int = 0,
        val Month:Int = 0,
        val Year:Int = 0){
        override fun toString(): String {
            return Day.toString() + "/" + (Month + 1) + "/" + Year
        }
    }

    data class Detail(
        val name: String? = null,
        val value:Double = 0.0,
        val arabicName: String? = null
    )
}