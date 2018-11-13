package com.blinnnk.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blinnnk.uikit.*
import com.blinnnk.util.observing

@SuppressLint("ViewConstructor")
class HoneyShineTextView(context: Context, type: ShineText) : View(context) {

  var titles: ShineTitles? by observing(null, didSet = { invalidate() })

  private val honey: Context.() -> Typeface? =
    fun Context.(): Typeface? = Typeface.createFromAsset(assets, "fonts/honeyFont.ttf")

  private var textCenterY: Float? = null
  private var subTextCenterY: Float? = null
  private var textSize: Float? = null
  private var subTextSize: Float? = null
  private var position = 0f
  private var paint = Paint()
  private var subPaint = Paint()
  private val progressTextSize = 64.uiPX().toFloat()
  private val topTextSize = 32.uiPX().toFloat()
  private val bottomTextSize = 24.uiPX().toFloat()
  private val subtitleTextSize = 16.uiPX().toFloat()
  private val speed = 24f
  private val centerColor = Color.WHITE
  private val edgeColor = Color.argb(60, 255, 255, 255)
  private val titleHeight = 70.uiPX()

  init {
    when (type) {
      ShineText.BottomTittle -> {
        textSize = bottomTextSize
        subTextSize = subtitleTextSize
        layoutParams = RelativeLayout.LayoutParams(matchParentViewGroup, titleHeight).apply {
          topMargin = 70.uiPX()
        }
        textCenterY = layoutParams.height / 2f + textSize!!
        subTextCenterY = layoutParams.height / 2f - subTextSize!!
        paint.typeface = Typeface.DEFAULT_BOLD
      }

      ShineText.TopTitle -> {
        textSize = topTextSize
        subTextSize = subtitleTextSize
        textCenterY = textSize!!
        subTextCenterY = textSize!! + subTextSize!! + 15.uiPX()
        layoutParams = LinearLayout.LayoutParams(matchParentViewGroup, titleHeight).apply {
          topMargin = 60.uiPX()
        }

        paint.typeface = Typeface.DEFAULT_BOLD
      }

      ShineText.Progress -> {
        textSize = progressTextSize
        subTextSize = 14.uiPX().toFloat()
        textCenterY = textSize!!
        subTextCenterY = textSize!! + subTextSize!! + 20.uiPX()
        layoutParams = RelativeLayout.LayoutParams(matchParentViewGroup, 120.uiPX()).apply {
          addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
          bottomMargin = 50.uiPX()
        }

        paint.typeface = honey(context) ?: Typeface.DEFAULT_BOLD
      }
    }

    subPaint.style = Paint.Style.FILL
    subPaint.isAntiAlias = true
    subPaint.textSize = subTextSize!!
    subPaint.textAlign = Paint.Align.CENTER
    subPaint.color = HoneyColor.Gray

    paint.style = Paint.Style.FILL
    paint.isAntiAlias = true
    paint.textSize = textSize!!
    paint.textAlign = Paint.Align.CENTER
    setPadding(15.uiPX(), paddingTop, 15.uiPX(), paddingBottom)

  }

  @SuppressLint("DrawAllocation") override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    paint.shader = RadialGradient(position, 0f, width.toFloat(), centerColor, edgeColor,
      Shader.TileMode.MIRROR
    )
    canvas.drawText(titles?.title.orEmpty(), width / 2f, textCenterY!!, paint)

    var y = subTextCenterY!!
    for (subtitle in titles?.subtitle!!.split("\n")) {
      canvas.drawText(subtitle, width / 2f, y, subPaint)
      y += subPaint.descent() - subPaint.ascent()
    }

    position += speed
    if (position > width * 100) {
      position = 0f
    }
    postInvalidateDelayed(10)
  }

}