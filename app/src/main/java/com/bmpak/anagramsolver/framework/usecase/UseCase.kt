package com.bmpak.anagramsolver.framework.usecase

import com.bmpak.anagramsolver.framework.arch.CoroutineContextProvider
import com.bmpak.anagramsolver.utils.Either
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

abstract class UseCase<out Type, in Params>(
    private var coroutineContextProvider: CoroutineContextProvider
) where Type : Any {

  protected abstract suspend fun run(params: Params): Either<Type, Throwable>

  fun execute(params: Params, onResult: suspend (Either<Type, Throwable>) -> Unit) {
    val job = async(coroutineContextProvider.asyncPool) { run(params) }
    launch(coroutineContextProvider.ui) { onResult.invoke(job.await()) }
  }
}
