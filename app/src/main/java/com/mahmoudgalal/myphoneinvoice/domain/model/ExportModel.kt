package com.mahmoudgalal.myphoneinvoice.domain.model

data class ExportModel(
    val filePath:String,
    val invoices:List<Invoice>
)
