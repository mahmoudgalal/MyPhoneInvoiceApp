package com.mahmoudgalal.myphoneinvoice.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudgalal.myphoneinvoice.domain.AbstractUseCase
import com.mahmoudgalal.myphoneinvoice.domain.model.ExportModel
import com.mahmoudgalal.myphoneinvoice.domain.model.Invoice
import com.mahmoudgalal.myphoneinvoice.domain.model.PhoneBillQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicesViewModel @Inject constructor(
    private val useCase: @JvmSuppressWildcards AbstractUseCase<PhoneBillQuery, List<Invoice>>,
    private val localInvoicesUseCase: @JvmSuppressWildcards  AbstractUseCase<Unit, List<Invoice>>,
    private val exportInvoicesUseCase: @JvmSuppressWildcards AbstractUseCase<ExportModel,Unit>
    ): ViewModel() {

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error
    private val _items:MutableLiveData<List<Invoice>> = MutableLiveData()
    val items: LiveData<List<Invoice>> = _items

    sealed class ExportState{
        object Started:ExportState()
        object Succeeded:ExportState()
        object Failed:ExportState()
        object Finished:ExportState()
        object NONE:ExportState()
    }

    private val _exportState:MutableLiveData<ExportState> = MutableLiveData()
    val exportState:LiveData<ExportState> = _exportState

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.message
    }

    fun fetchPhoneInvoices(areaCode: String, phoneNumber: String) {
        viewModelScope.launch(errorHandler) {
            val result = useCase(
                PhoneBillQuery(
                    areaCode, phoneNumber
                )
            )
            _items.value = result.fold({
                it
            }) {
                _error.value = it.message
                emptyList()
            }
        }
    }

    fun loadAllInvoices(){
        viewModelScope.launch(errorHandler) {
            val result = localInvoicesUseCase(Unit)
            _items.value = result.fold({
                it
            }) {
                _error.value = it.message
                emptyList()
            }
        }
    }

    fun exportAllInvoicesToXLSX(path: String) {
        viewModelScope.launch(errorHandler) {
            _items.value?.let {
                _exportState.value = ExportState.Started
                val result = exportInvoicesUseCase(ExportModel(path, it))
                _exportState.value = ExportState.Finished
                if(result.isSuccess)
                    _exportState.value = ExportState.Succeeded
                else
                    _error.value = result.exceptionOrNull()?.message ?:"Error"
                _exportState.value = ExportState.NONE
            }
        }
    }

}