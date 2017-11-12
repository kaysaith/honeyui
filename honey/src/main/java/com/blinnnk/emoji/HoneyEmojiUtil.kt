package com.blinnnk.emoji

import android.graphics.*

/**
 * @date 24/10/2017
 * @author Rita
 */

object EmojiUI {
  val duration: Float = 1600f
  val speedTime: Float = 10f
  val lineColor = Color.BLACK
  val faceColor = Color.rgb(255, 207, 0)
  val handColor = Color.rgb(255, 144, 0)
  val tongueColor = Color.rgb(240, 82, 104)
  val tearColor = Color.rgb(70, 192, 230)
  val lightBlue = Color.rgb(126, 211, 255)
  val darkBlue = Color.rgb(0, 111, 155)
  val lightGray = Color.rgb(238, 238, 238)
  val lineWidthRate = 0.05f
}

object EmojiPaint {

  /** 画线性渐变色的脸
   * <h5>画一个渐变的脸，并且指定颜色和填充位置。</h5>
   * @param [positions] <FloatArray> 颜色对应的位置序列, [emojiSize] <Float> emoji的直径
   */
  fun drawGradientFace(
    canvas: Canvas,
    colors: IntArray,
    positions: FloatArray,
    emojiSize: Float) {

    val gradient = LinearGradient(0f, 0f, 0f, emojiSize,
      colors,
      positions,
      Shader.TileMode.CLAMP
    )
    val paint = Paint()
    paint.isAntiAlias = true
    paint.shader = gradient
    canvas.drawCircle(emojiSize / 2, emojiSize / 2, emojiSize / 2, paint)

  }

  /** emoji的实心paint
   * 用于眼睛等需要填充的实心paint样式
   * @param <color> emoji的填充色
   * @return <Paint> emoji的线条画笔样式
   */
  fun solidPaint(color: Int): Paint {
    val paint = Paint()
    paint.reset()
    paint.isAntiAlias = true
    paint.color = color
    paint.style = Paint.Style.FILL
    return paint
  }

  /** `emoji` 的脸颊 `paint` 用于脸颊的渐变红色填充
   * @param [centerX] 渐变圆心x  [centerY] 渐变圆心y
   * @return <Paint> emoji的脸颊画笔样式
   */
  fun emojiCheekPaint(
    centerX: Float,
    centerY: Float,
    radius: Float): Paint {

    val gradientColors = intArrayOf(Color.argb(100, 255, 0, 0),
      Color.argb(0, 255, 0, 0)
    )
    val gradientPositions = floatArrayOf(0f, radius)
    val paint = Paint()

    val gradient = RadialGradient(centerX,
      centerY,
      radius,
      gradientColors,
      gradientPositions,
      Shader.TileMode.CLAMP
    )

    paint.shader = gradient

    return paint
  }

  /** `emoji` 的线条 `paint` 用于嘴巴、眉毛的 `paint` 样式
   * @param [emojiSize] emoji的直径，用来计算线条的粗细
   * @return <Paint> emoji的线条画笔样式
   */
  fun emojiStrokePaint(emojiSize: Float): Paint {
    val paint = Paint()
    paint.reset()
    paint.isAntiAlias = true
    paint.color = EmojiUI.lineColor
    paint.strokeWidth = emojiSize * EmojiUI.lineWidthRate
    paint.strokeCap = Paint.Cap.ROUND
    paint.style = Paint.Style.STROKE
    return paint
  }
}