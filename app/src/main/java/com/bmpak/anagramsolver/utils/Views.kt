package com.bmpak.anagramsolver.utils

import android.animation.Animator
import android.graphics.Point
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import com.bmpak.anagramsolver.utils.animations.SimpleAnimator

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun View.visibleOrGone(shouldBeVisible: Boolean) {
  if (shouldBeVisible) {
    visible()
  } else {
    gone()
  }
}

fun View.visibleOrInvisible(shouldBeVisible: Boolean) {
  if (shouldBeVisible) {
    visible()
  } else {
    invisible()
  }
}

inline fun ViewPropertyAnimator.onEnd(
    crossinline func: (animator: Animator) -> Unit
): ViewPropertyAnimator = setListener(object : SimpleAnimator() {
  override fun onAnimationEnd(p0: Animator) {
    func(p0)
  }
})

inline fun ViewPropertyAnimator.onMiddle(
    crossinline func: (animator: Animator) -> Unit
): ViewPropertyAnimator {
  var isFuncInvoked = false
  setUpdateListener {
    if (it.animatedFraction >= 0.5 && !isFuncInvoked) {
      func(it)
      isFuncInvoked = true
    }
  }

  return this
}

inline fun Animator.onEnd(
    crossinline func: (animator: Animator) -> Unit
) = addListener(object : SimpleAnimator() {
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

/**
 * Updates this view's padding. This version of the method allows using named parameters
 * to just set one or more axes.
 *
 * @see View.setPadding
 */
fun View.updatePadding(
    @Px left: Int = paddingLeft,
    @Px top: Int = paddingTop,
    @Px right: Int = paddingRight,
    @Px bottom: Int = paddingBottom
) {
  setPadding(left, top, right, bottom)
}
