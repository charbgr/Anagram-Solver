package com.bmpak.anagramsolver.ui.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import com.bmpak.anagramsolver.R


class TextFlipper : TextSwitcher {

  private val DEFAULT_DURATION = 250L

  var animationDuration: Long = DEFAULT_DURATION

  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

  override fun onFinishInflate() {
    super.onFinishInflate()
    if (isInEditMode) return
    setFactory {
      TextView(ContextThemeWrapper(context, R.style.Text_Onboarding)).apply {
        gravity = Gravity.CENTER
      }
    }
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
    animateUp()
  }

  fun setText(@StringRes resId: Int, vararg objects: Any) {
    setText(context.getString(resId, *objects))
  }

  fun setTextDelayed(@StringRes resId: Int, delayMillis: Long, vararg objects: Any) {
    postDelayed({ setText(resId, objects) }, delayMillis)
  }

  fun setCurrentText(@StringRes resId: Int, vararg objects: Any) {
    setCurrentText(context.getString(resId, *objects))
  }

  fun animateUp() {
    val offset = height * 0.75f
    inAnimation = createAnimation(
        fromY = offset,
        toY = 0f,
        fromAlpha = 0f,
        toAlpha = 1f
    )
    outAnimation = createAnimation(
        fromY = 0f,
        toY = -offset,
        fromAlpha = 1f,
        toAlpha = 0f
    )
  }

  fun animateDown() {
    val offset = height * 0.75f
    inAnimation = createAnimation(
        fromY = -offset,
        toY = 0f,
        fromAlpha = 0f,
        toAlpha = 1f
    )
    outAnimation = createAnimation(
        fromY = 0f,
        toY = offset,
        fromAlpha = 1f,
        toAlpha = 0f
    )
  }

  private fun createAnimation(
      fromY: Float,
      toY: Float,
      fromAlpha: Float,
      toAlpha: Float
  ): Animation {
    val translate = TranslateAnimation(0f, 0f, fromY, toY)
    translate.duration = animationDuration

    val alpha = AlphaAnimation(fromAlpha, toAlpha)
    alpha.duration = animationDuration

    val set = AnimationSet(true)
    set.interpolator = DecelerateInterpolator()
    set.addAnimation(translate)
    set.addAnimation(alpha)
    return set
  }
}
