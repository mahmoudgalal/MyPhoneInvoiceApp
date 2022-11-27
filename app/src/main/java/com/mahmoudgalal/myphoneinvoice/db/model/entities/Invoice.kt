package com.mahmoudgalal.myphoneinvoice.db.model.entities

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse

//@Entity(tableName = "Invoices")
data class Invoice(
    @PrimaryKey
    val id: String,
    val areaCode: String? = null,
    val phoneNumber: String? = null,
    val totalAmount:Double = 0.0,
    @Embedded(prefix = "CS_")
    val consumptionStart: ServiceResponse.Account.Invoice.DateStruct? = null,
    @Embedded(prefix = "CE_")
    val consumptionEnd: ServiceResponse.Account.Invoice.DateStruct? = null,
    @Embedded(prefix = "SS_")
    val subscribtionStart: ServiceResponse.Account.Invoice.DateStruct? = null,
    @Embedded(prefix = "SE_")
    var subscribtionEnd: ServiceResponse.Account.Invoice.DateStruct? = null,
    // public int Status
    val paymentGateNameAr: String? = null,
    var status:Int = 0,
    var details: List<ServiceResponse.Account.Invoice.Detail>? = null
)
