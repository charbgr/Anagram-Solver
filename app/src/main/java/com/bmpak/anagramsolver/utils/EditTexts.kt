package com.bmpak.anagramsolver.utils

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
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

open class SimpleTextWatcher: TextWatcher {
  override fun afterTextChanged(editable: Editable) {
  }

  override fun beforeTextChanged(text: CharSequence, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
  }
}
