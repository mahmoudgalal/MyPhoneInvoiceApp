package com.mahmoudgalal.myphoneinvoice.data.di

import com.mahmoudgalal.myphoneinvoice.data.InvoicesRepositoryImpl
import com.mahmoudgalal.myphoneinvoice.data.repository.InvoicesRepository
import com.mahmoudgalal.myphoneinvoice.db.model.dao.InvoicesDAO
import com.mahmoudgalal.myphoneinvoice.network.endpoints.TelephoneInvoiceService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideInvoicesRepository(
        invoiceService: TelephoneInvoiceService,
        invoicesDAO: InvoicesDAO
    ): InvoicesRepository{
        return InvoicesRepositoryImpl(invoiceService,invoicesDAO)
    }
}