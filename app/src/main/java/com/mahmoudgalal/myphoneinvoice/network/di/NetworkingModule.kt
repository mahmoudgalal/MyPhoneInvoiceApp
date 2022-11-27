package com.mahmoudgalal.myphoneinvoice.network.di

import com.mahmoudgalal.myphoneinvoice.network.endpoints.TelephoneInvoiceService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    @Provides
    @Singleton
    fun provideTelephoneInvoiceService(): TelephoneInvoiceService {
        return TelephoneInvoiceService.instance
    }
}