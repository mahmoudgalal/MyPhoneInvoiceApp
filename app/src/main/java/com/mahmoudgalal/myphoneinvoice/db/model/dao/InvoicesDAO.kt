package com.mahmoudgalal.myphoneinvoice.db.model.dao

import androidx.room.*
import com.mahmoudgalal.myphoneinvoice.network.model.ServiceResponse.Account.Invoice

@Dao
interface InvoicesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInvoices(invoices: List<Invoice>?)

    @Update
    fun updateInvoices(invoices: List<Invoice>): Int

    @Delete
    fun deleteInvoices(invoices: List<Invoice>): Int

    @get:Query("SELECT * FROM Invoices")
    val allInvoices: List<Invoice>
}