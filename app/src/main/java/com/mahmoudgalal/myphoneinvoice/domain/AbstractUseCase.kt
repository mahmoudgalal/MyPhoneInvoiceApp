package com.mahmoudgalal.myphoneinvoice.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class AbstractUseCase<in Input, Output>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(param: Input): Result<Output> {
        return withContext(coroutineDispatcher) {
            execute(param)
        }
    }

    abstract suspend fun execute(param: Input): Result<Output>
}