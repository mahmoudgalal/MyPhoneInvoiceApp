package com.mahmoudgalal.myphoneinvoice.domain

import com.mahmoudgalal.myphoneinvoice.common.Mapper
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice
import com.mahmoudgalal.myphoneinvoice.data.model.Invoice as DataInvoice
import com.mahmoudgalal.myphoneinvoice.domain.model.PhoneBillQuery
import com.mahmoudgalal.myphoneinvoice.data.repository.InvoicesRepository
import com.mahmoudgalal.myphoneinvoice.domain.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InvoicesUseCase @Inject constructor (
    @IoDispatcher
    coroutineDispatcher: CoroutineDispatcher,
    private val invoicesRepository: InvoicesRepository,
    private val mapper: Mapper<List<DataInvoice>, List<Invoice>>
    ): AbstractUseCase<PhoneBillQuery,List<Invoice>>(coroutineDispatcher) {

    override suspend fun execute(query: PhoneBillQuery): Result<List<Invoice>> {
        val result =  invoicesRepository.fetchInvoicesForPhone(query.areaCode,query.phoneNumber)
        return if(result.isSuccess) result.map {
            mapper map it
        } else
            Result.failure(result.exceptionOrNull()!!)
    }
}