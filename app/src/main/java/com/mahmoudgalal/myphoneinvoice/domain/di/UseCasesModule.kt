package com.mahmoudgalal.myphoneinvoice.domain.di

import com.mahmoudgalal.myphoneinvoice.common.Mapper
import com.mahmoudgalal.myphoneinvoice.data.repository.InvoicesRepository
import com.mahmoudgalal.myphoneinvoice.domain.AbstractUseCase
import com.mahmoudgalal.myphoneinvoice.domain.ExportInvoicesUseCase
import com.mahmoudgalal.myphoneinvoice.domain.InvoicesUseCase
import com.mahmoudgalal.myphoneinvoice.domain.LocalInvoicesUseCase
import com.mahmoudgalal.myphoneinvoice.domain.mappers.DataToDomainMapper
import com.mahmoudgalal.myphoneinvoice.domain.mappers.DomainToDataMapper
import com.mahmoudgalal.myphoneinvoice.domain.model.ExportModel
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice as DomainInvoice
import com.mahmoudgalal.myphoneinvoice.data.model.Invoice as DataInvoice
import com.mahmoudgalal.myphoneinvoice.domain.model.PhoneBillQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun provideInvoicesUseCase(
        @IoDispatcher
        coroutineDispatcher: CoroutineDispatcher,
        invoicesRepository: InvoicesRepository,
        mapper: @JvmSuppressWildcards Mapper<List<DataInvoice>, List<DomainInvoice>>
    ):@JvmSuppressWildcards AbstractUseCase<PhoneBillQuery, List<DomainInvoice>>{
        return InvoicesUseCase(coroutineDispatcher,invoicesRepository,mapper)
    }

    @Provides
    fun provideLocalInvoicesUseCase(
        @IoDispatcher
        coroutineDispatcher: CoroutineDispatcher,
        invoicesRepository: InvoicesRepository,
        mapper: @JvmSuppressWildcards Mapper<List<DataInvoice>, List<DomainInvoice>>
    ):@JvmSuppressWildcards AbstractUseCase<Unit, List<DomainInvoice>>{
        return LocalInvoicesUseCase(coroutineDispatcher,invoicesRepository,mapper)
    }

    @Provides
    fun provideExportInvoicesUseCase(
        @IoDispatcher
        coroutineDispatcher: CoroutineDispatcher,
        invoicesRepository: InvoicesRepository,
        mapper: @JvmSuppressWildcards Mapper<List<DomainInvoice>, List<DataInvoice>>
    ):@JvmSuppressWildcards AbstractUseCase<ExportModel,Unit>{
        return ExportInvoicesUseCase(coroutineDispatcher,invoicesRepository,mapper)
    }

    @Provides
    fun provideDataToDomainMapper():@JvmSuppressWildcards Mapper<List<DataInvoice>, List<DomainInvoice>>{
        return DataToDomainMapper()
    }

    @Provides
    fun provideDomainToDataMapper():@JvmSuppressWildcards Mapper<List<DomainInvoice>, List< DataInvoice>>{
        return DomainToDataMapper()
    }

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}