package com.bmpak.anagramsolver

import com.google.common.truth.Truth.assertThat
import java.util.concurrent.CopyOnWriteArrayList

class Tape<T : Any?> {

  companion object {
    fun <T : Any> create(): Tape<T> = Tape()
    fun <T : Any> createDefault(defaultRender: T): Tape<T> {
      return Tape<T>().apply { add(defaultRender) }
    }
  }

  val renders: CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

  fun add(render: T) {
    renders.add(render)
  }

  fun assertRenders(vararg expectedList: T) = apply {
    assertThat(expectedList).asList().containsExactlyElementsIn(renders)
  }

  fun assertRenderedOnce() {
    assertRenderedTimes(1)
  }

  fun assertRenderedTimes(times: Int) {
    assertThat(renders).hasSize(times)
    assertThat(times).isEqualTo(renders.size)
  }

  fun assertRenderedAtLeastOnce() {
    assertThat(renders.size).isAtLeast(1)
  }

  fun assertNoRenders() = apply {
    assertRenderedTimes(0)
  }

  fun reset() = apply {
    renders.clear()
  }
}
