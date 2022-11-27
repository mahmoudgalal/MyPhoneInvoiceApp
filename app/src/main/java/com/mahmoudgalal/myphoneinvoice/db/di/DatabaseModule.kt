package com.mahmoudgalal.myphoneinvoice.db.di

import android.content.Context
import com.mahmoudgalal.myphoneinvoice.db.AppDatabase
import com.mahmoudgalal.myphoneinvoice.db.model.dao.InvoicesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideInvoicesDAO(
        @ApplicationContext applicationContext: Context
    ): InvoicesDAO {
        return AppDatabase.getInstance(applicationContext).invoiceDao()
    }
}