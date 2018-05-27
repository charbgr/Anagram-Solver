package com.bmpak.anagramsolver.utils

sealed class Either<out L, out R> {
  /** * Represents the left side of [Either] class which by convention is a "Failure". */
  data class Left<out L>(val value: L) : Either<L, Nothing>()

  /** * Represents the right side of [Either] class which by convention is a "Success". */
  data class Right<out R>(val value: R) : Either<Nothing, R>()

  val isRight get() = this is Right<R>
  val isLeft get() = this is Left<L>

  fun <L> left(a: L) = Either.Left(a)
  fun <R> right(b: R) = Either.Right(b)

  fun either(onLeft: (L) -> Unit, onRight: (R) -> Unit) {
    when (this) {
      is Left -> onLeft(value)
      is Right -> onRight(value)
    }
  }
}
