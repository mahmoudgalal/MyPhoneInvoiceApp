package com.mahmoudgalal.myphoneinvoice.domain

import com.mahmoudgalal.myphoneinvoice.common.Mapper
import com.mahmoudgalal.myphoneinvoice.data.repository.InvoicesRepository
import com.mahmoudgalal.myphoneinvoice.domain.di.IoDispatcher
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice
import com.mahmoudgalal.myphoneinvoice.data.model.Invoice as DataInvoice
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LocalInvoicesUseCase@Inject constructor (
    @IoDispatcher
    coroutineDispatcher: CoroutineDispatcher,
    private val invoicesRepository: InvoicesRepository,
    private val mapper: Mapper<List<DataInvoice>, List<Invoice>>
): AbstractUseCase<Unit, List<Invoice>>(coroutineDispatcher) {
    override suspend fun execute(param: Unit): Result<List<Invoice>> {
        return invoicesRepository.getAllInvoices().map {
            mapper map it
        }
    }
}