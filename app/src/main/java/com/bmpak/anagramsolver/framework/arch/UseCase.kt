package com.bmpak.anagramsolver.framework.arch

import com.bmpak.anagramsolver.utils.Either
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

abstract class UseCase<out Type, in Params> where Type : Any {
  protected abstract suspend fun run(params: Params): Either<Type, Throwable>

  fun execute(params: Params, onResult: (Either<Type, Throwable>) -> Unit) {
    val job = async(CommonPool) { run(params) }
    launch(UI) { onResult.invoke(job.await()) }
  }
}
