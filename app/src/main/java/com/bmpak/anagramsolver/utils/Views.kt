package com.bmpak.anagramsolver.utils

import android.animation.Animator
import android.graphics.Point
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.core.view.ViewCompat
import com.bmpak.anagramsolver.utils.animations.SimpleValueAnimator

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

internal inline fun ViewPropertyAnimator.onEnd(
    crossinline func: (animator: Animator) -> Unit
): ViewPropertyAnimator = setListener(object : SimpleValueAnimator() {
  override fun onAnimationEnd(p0: Animator) {
    func(p0)
  }
})

inline val View.locationInWindow: Point
  get() = if (!ViewCompat.isLaidOut(this)) {
    throw IllegalStateException("View needs to be laid out before calling locationInWindow")
  } else {
    IntArray(2).let {
      getLocationInWindow(it)
      Point(it[0], it[1])
    }
  }

inline val View.locationOnScreen: Point
  get() = if (!ViewCompat.isLaidOut(this)) {
    throw IllegalStateException("View needs to be laid out before calling locationOnScreen")
  } else {
    IntArray(2).let {
      getLocationOnScreen(it)
      Point(it[0], it[1])
    }
  }