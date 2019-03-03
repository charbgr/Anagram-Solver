package com.bmpak.anagramsolver.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.utils.animations.ScaleXYProperty

/**
 * Basic implementation for any flag.
 *
 * Needs to be updated with an Animator in order to make the progressbar transit smoothly.
 * Current implementation is choppy.
 */
class FlagView : View {

  private val pickBackgroundDrawable by lazy {
    val oval = ShapeDrawable(OvalShape())
    oval.intrinsicHeight = height
    oval.intrinsicWidth = width
    oval.paint.color = ContextCompat.getColor(context, R.color.white)
    oval
  }

  private val DEFAULT_ELEVATION = 8f
  var pickElevation: Float = DEFAULT_ELEVATION

  var isPicked = false
    private set

  var progress: Float = 0f
    set(value) {
      field = value
      invalidate()
    }

  @ColorRes
  var progressColor: Int = R.color.white
    set(value) {
      field = value
      invalidate()
    }

  var progressWidth = 12f
    set(value) {
      field = value
      invalidate()
    }

  private val progressAngle: Float
    get() = progress / 100 * 360f

  private lateinit var progressPaint: Paint
  private val progressRectF: RectF by lazy {
    RectF()
  }

  private lateinit var flagDrawable: Drawable

  constructor(context: Context?) : this(context, null)
  constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
    defStyleAttr) {
    readAttributes(attrs, defStyleAttr)
    setup()
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas ?: return

    val bitmap: Bitmap = getBitmapFromFlag()
    canvas.drawBitmap(bitmap, (width - bitmap.width) / 2.0f, (height - bitmap.height) / 2.0f, null)

    progressRectF.set(
      progressWidth, // left
      progressWidth, // top
      width.toFloat() - progressWidth, // right
      height.toFloat() - progressWidth // bottom
    )
    canvas.drawArc(progressRectF, -90f, progressAngle, false, progressPaint)
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

  private fun readAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
    attrs ?: return

    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlagView, defStyleAttr, 0)
    flagDrawable = typedArray.getDrawable(R.styleable.FlagView_flagSrc)
      ?: throw IllegalArgumentException("Invalid drawable resource id.")
    typedArray.recycle()
  }

  private fun setup() {
    progressPaint = Paint()
    progressPaint.style = Paint.Style.STROKE
    progressPaint.strokeWidth = progressWidth
    progressPaint.isAntiAlias = true
    progressPaint.color = ContextCompat.getColor(context, progressColor)
  }

  private fun getBitmapFromFlag(): Bitmap {
    val bitmapWidth = width - progressWidth.toInt()
    val bitmapHeight = height - progressWidth.toInt()
    val bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    flagDrawable.setBounds(0, 0, bitmapWidth, bitmapHeight)
    flagDrawable.draw(canvas)
    return bitmap
  }
}
