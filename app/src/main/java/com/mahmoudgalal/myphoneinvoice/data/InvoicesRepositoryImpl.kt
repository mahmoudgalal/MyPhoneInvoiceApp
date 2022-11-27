package com.mahmoudgalal.myphoneinvoice.data

import androidx.annotation.WorkerThread
import com.mahmoudgalal.myphoneinvoice.Utils.generateRandomToken
import com.mahmoudgalal.myphoneinvoice.data.model.Invoice
import com.mahmoudgalal.myphoneinvoice.data.repository.InvoicesRepository
import com.mahmoudgalal.myphoneinvoice.db.model.dao.InvoicesDAO
import com.mahmoudgalal.myphoneinvoice.network.endpoints.TelephoneInvoiceService
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import javax.inject.Inject
import com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse.Account.Invoice as NetworkInvoice

class InvoicesRepositoryImpl  @Inject constructor(
    private val invoiceService: TelephoneInvoiceService,
    private val invoicesDAO: InvoicesDAO
    ) : InvoicesRepository {
    override suspend fun fetchInvoicesForPhone(
        areaCode: String,
        phoneNumber: String
    ): Result<List<Invoice>> = try {
        val res = invoiceService.getTelephoneInvoices(generateRandomToken(), "telephone", areaCode, phoneNumber)
        res.account?.invoices?.let {
            invoicesDAO.insertInvoices(it)
        }
        Result.success(res.account?.invoices?.toInvoices()?: emptyList())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getAllInvoices(): Result<List<Invoice>> {
        return Result.success(invoicesDAO.allInvoices.toInvoices())
    }

    @Throws(java.lang.Exception::class)
    @WorkerThread
    override suspend fun exportInvoicesToXLSX(filePath: String, invoices: List<Invoice>) {
        return try {
            val Headers = arrayOf(
                "Area Code", "Phone Number", "Payment Gate", "Total Amount",
                "Consumption From", "Consumption To", "Subscription From", "Subscription To"
            )
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("mysheet"))
            for (i in -1 until invoices.size) {
                val row: Row = sheet.createRow(i + 1)
                if (i < 0) {
                    for (j in Headers.indices) {
                        val cell = row.createCell(j)
                        cell.setCellValue(Headers[j])
                        //cell.getCellStyle().set
                    }
                    continue
                }
                val invoice = invoices[i]
                val cell = row.createCell(0)
                cell.setCellValue(invoice.areaCode)
                val cell1 = row.createCell(1)
                cell1.setCellValue(invoice.phoneNumber)
                val cell2 = row.createCell(2)
                cell2.setCellValue(if (invoice.paymentGateNameAr == null || invoice.status < 1) "غير مدفوعه" else invoice.paymentGateNameAr)
                val cell3 = row.createCell(3)
                cell3.setCellValue(invoice.totalAmount)
                val cell4 = row.createCell(4)
                cell4.setCellValue(invoice.consumptionStart.toString())
                val cell5 = row.createCell(5)
                cell5.setCellValue(invoice.consumptionEnd.toString())
                val cell6 = row.createCell(6)
                cell6.setCellValue(invoice.subscriptionStart.toString())
                val cell7 = row.createCell(7)
                cell7.setCellValue(invoice.subscriptionEnd.toString())
            }
            val outFileName = "Invoices.xlsx"
            val outFile = File(filePath, outFileName)
            outFile.outputStream().use {
                workbook.write(it)
                it.flush()
            }
            //val outputStream: OutputStream = FileOutputStream(outFile.absolutePath)

        } catch (e:Exception){
            throw e
        }
    }


    private fun List<NetworkInvoice>.toInvoices(): List<Invoice> =
        this.map {
            Invoice(
                id = it.id,
                areaCode = it.areaCode,
                phoneNumber = it.phoneNumber,
                totalAmount = it.totalAmount,
                consumptionStart = it.consumptionStart?.let { cs ->
                    Invoice.DateStruct(
                        cs.Sec,
                        cs.Min, cs.Hour, cs.Day, cs.Month, cs.Year
                    )
                },
                consumptionEnd = it.consumptionEnd?.let { ce ->
                    Invoice.DateStruct(
                        ce.Sec,
                        ce.Min, ce.Hour, ce.Day, ce.Month, ce.Year
                    )
                },
                subscriptionStart = it.subscribtionStart?.let { ss ->
                    Invoice.DateStruct(
                        ss.Sec,
                        ss.Min, ss.Hour, ss.Day, ss.Month, ss.Year
                    )
                },
                subscriptionEnd = it.subscribtionEnd?.let { se ->
                    Invoice.DateStruct(
                        se.Sec,
                        se.Min, se.Hour, se.Day, se.Month, se.Year
                    )
                },
                paymentGateNameAr = it.paymentGateNameAr,
                status = it.status,
                details = it.details?.map { d ->
                    Invoice.Detail(
                        d.name,
                        d.value,
                        d.arabicName
                    )
                }
            )
        }


}