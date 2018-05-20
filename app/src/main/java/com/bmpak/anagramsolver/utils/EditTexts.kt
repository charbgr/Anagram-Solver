package com.bmpak.anagramsolver.utils

import android.text.InputFilter
import android.widget.EditText

fun EditText.disableNumberAndSpaceInput() {
  val numberInputFiler = InputFilter { source, start, end, _, _, _ ->
    for (i in start until end) {
      if (!Character.isLetter(source[i]) || Character.isSpaceChar(source[i])) {
        return@InputFilter ""
      }
    }
    null
  }

  this.filters = arrayOf(numberInputFiler)
}
