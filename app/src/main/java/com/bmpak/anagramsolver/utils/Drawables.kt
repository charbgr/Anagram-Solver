package com.bmpak.anagramsolver.utils

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat

inline fun Context.createAvd(
    @DrawableRes resId: Int,
    crossinline func: (animatable: Animatable) -> Unit = {}
): Animatable? {
  val animatable = AnimatedVectorDrawableCompat.create(this, resId)
  if (animatable != null) {
    func(animatable)
  }
  return animatable
}

inline fun Context.createAd(
    @DrawableRes resId: Int,
    crossinline func: (drawable: AnimationDrawable) -> Unit = {}
): AnimationDrawable? {
  val drawable = ContextCompat.getDrawable(this, resId) as? AnimationDrawable
  drawable ?: return null
  func(drawable)
  return drawable
}
