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

internal inline fun EditText.onTextChanged(
    crossinline func: (text: CharSequence) -> Unit
): TextWatcher {
  val watcher = object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) = func(text)
    override fun afterTextChanged(s: Editable?) = Unit
  }
  addTextChangedListener(watcher)
  return watcher
}
