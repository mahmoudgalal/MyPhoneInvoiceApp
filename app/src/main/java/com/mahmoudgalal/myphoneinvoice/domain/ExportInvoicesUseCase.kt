package com.mahmoudgalal.myphoneinvoice.domain

import com.mahmoudgalal.myphoneinvoice.common.Mapper
import com.mahmoudgalal.myphoneinvoice.data.model.Invoice as DataInvoice
import com.mahmoudgalal.myphoneinvoice.data.repository.InvoicesRepository
import com.mahmoudgalal.myphoneinvoice.domain.di.IoDispatcher
import com.mahmoudgalal.myphoneinvoice.domain.model.ExportModel
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice as DomainInvoice
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import kotlin.Exception

class ExportInvoicesUseCase @Inject constructor (
    @IoDispatcher
    coroutineDispatcher: CoroutineDispatcher,
    private val invoicesRepository: InvoicesRepository,
    private val mapper: Mapper<List<DomainInvoice>, List<DataInvoice>>
): AbstractUseCase<ExportModel,Unit>(coroutineDispatcher) {
    override suspend fun execute(exportModel: ExportModel): Result<Unit> {
        return try {
            invoicesRepository.exportInvoicesToXLSX(exportModel.filePath,mapper map exportModel.invoices)
            Result.success(Unit)
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}