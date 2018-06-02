package com.bmpak.anagramsolver.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.utils.animations.ScaleXYProperty


class FlagView : AppCompatImageView {

  private val pickBackgroundDrawable by lazy {
    val oval = ShapeDrawable(OvalShape())
    oval.intrinsicHeight = height
    oval.intrinsicWidth = width
    oval.paint.color = ContextCompat.getColor(context, R.color.white)
    oval
  }

  private val DEFAULT_ELEVATION = 12f
  var pickElevation: Float = DEFAULT_ELEVATION

  var isPicked = false
    private set

  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr)

  init {
    val borderSize = context.resources.getDimensionPixelSize(R.dimen.flag_border)
    updatePadding(borderSize, borderSize, borderSize, borderSize)
  }

  fun pick() {
    if (isPicked) {
      return
    }

    background = pickBackgroundDrawable
    AnimatorSet().apply {
      playTogether(
          ObjectAnimator.ofFloat(this@FlagView, ScaleXYProperty(), 1.05f),
          ObjectAnimator.ofFloat(this@FlagView, "elevation", pickElevation)
      )
      duration = 200
      interpolator = AccelerateInterpolator()
      start()
    }
    isPicked = true
  }

  fun unpick() {
    if (!isPicked) {
      return
    }

    background = null
    AnimatorSet().apply {
      playTogether(
          ObjectAnimator.ofFloat(this@FlagView, ScaleXYProperty(), 1f),
          ObjectAnimator.ofFloat(this@FlagView, "elevation", 0f)
      )
      duration = 200
      interpolator = DecelerateInterpolator()
      start()
    }
    isPicked = false
  }
}
