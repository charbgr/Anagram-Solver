package com.bmpak.anagramsolver.utils.animations

import android.util.Property
import android.view.View

class ScaleXYProperty : Property<View, Float>(Float::class.java, "ScaleXYProperty") {

  override fun get(view: View): Float = view.scaleX

  override fun set(view: View, value: Float) {
    view.scaleX = value
    view.scaleY = value
  }
}
