package com.mahmoudgalal.myphoneinvoice.data.repository

import com.mahmoudgalal.myphoneinvoice.data.model.Invoice
import java.lang.Exception


interface InvoicesRepository {
    suspend fun fetchInvoicesForPhone(areaCode: String, phoneNumber: String):Result<List<Invoice>>
    suspend fun getAllInvoices():Result<List<Invoice>>
    @Throws(Exception::class)
    suspend fun exportInvoicesToXLSX(filePath:String,invoices:List<Invoice>):Unit
}