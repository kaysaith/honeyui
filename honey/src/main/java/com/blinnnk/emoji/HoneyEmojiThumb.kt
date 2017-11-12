package com.blinnnk.emoji

import android.graphics.*
import com.blinnnk.util.angleToRadian

/**
 * @date 24/10/2017
 * @author Rita
 */

fun loadThumbEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  marginX: Float,
  marginY: Float,
  size: Float,
  isAnimate: Boolean) {
  val mainKeyTimes = floatArrayOf(0f, 0.1f, 0.135f, 0.17f, 0.45f, 0.5f, 0.75f, 0.825f, 1f)
  val proceed: Float = currentTime / duration % 1f

  val faceMaxRotate = 8f
  val winkMaxRotate: Float = -8f
  val thumbMaxRotate = 15f

  var faceRotate: Float = faceMaxRotate
  var isWink = false
  var winkRotate = 0f
  var thumbOffsetRate = 0f
  var thumbRotate: Float = thumbMaxRotate
  var thumbAlpha = 255
  var starOffsetRate = 0f
  var starAlpha = 0

  if (isAnimate) {

    if (proceed >= mainKeyTimes[1] && proceed < mainKeyTimes[5]) {
      if (proceed >= mainKeyTimes[1] && proceed < mainKeyTimes[2]) {
        // 【1 ~ 2】
        val currentPercent = (proceed - mainKeyTimes[1]) / (mainKeyTimes[2] - mainKeyTimes[1])
        isWink = true
        winkRotate = winkMaxRotate * currentPercent
        starAlpha = (255 * currentPercent).toInt()

      } else if (proceed >= mainKeyTimes[2] && proceed < mainKeyTimes[3]) {
        // 【2 ~ 3】
        val currentPercent = (proceed - mainKeyTimes[2]) / (mainKeyTimes[3] - mainKeyTimes[2])
        isWink = true
        winkRotate = winkMaxRotate * (1f - currentPercent)
        starAlpha = 255

      } else if (proceed >= mainKeyTimes[3] && proceed < mainKeyTimes[4]) {
        // 【3 ~ 4】
        isWink = true
        starAlpha = 255

      } else if (proceed >= mainKeyTimes[4] && proceed < mainKeyTimes[5]) {
        // 【4 ~ 5】
        val currentPercent = (proceed - mainKeyTimes[4]) / (mainKeyTimes[5] - mainKeyTimes[4])
        isWink = true
        starAlpha = (255f * (1f - currentPercent)).toInt()
      }
      // 【1 ~ 5】
      // star位置变化
      val currentPercent = (proceed - mainKeyTimes[1]) / (mainKeyTimes[5] - mainKeyTimes[1])
      starOffsetRate = currentPercent

    } else if (proceed >= mainKeyTimes[5] && proceed < mainKeyTimes[6]) {
      // 【5 ~ 6】
      val currentPercent = (proceed - mainKeyTimes[5]) / (mainKeyTimes[6] - mainKeyTimes[5])
      faceRotate = faceMaxRotate * (1f - currentPercent)
      thumbRotate = thumbMaxRotate * (1f - currentPercent)
      thumbOffsetRate = currentPercent
      thumbAlpha = (255f * (1f - currentPercent)).toInt()

    } else if (proceed >= mainKeyTimes[6]) {

      if (proceed < mainKeyTimes[7]) {
        // 【6~7】
        thumbOffsetRate = 1f
        thumbRotate = 0f
        thumbAlpha = 0

      } else {
        // 【7~ End】
        val currentPercent = (proceed - mainKeyTimes[7]) / (1f - mainKeyTimes[7])
        // 手回到脸中心
        thumbOffsetRate = 1f - currentPercent
        thumbRotate = thumbMaxRotate * currentPercent
        thumbAlpha = (255f * currentPercent).toInt()
      }
      //【6~ End】
      // 旋转角度恢复
      val currentPercent = (proceed - mainKeyTimes[6]) / (1f - mainKeyTimes[6])
      faceRotate = faceMaxRotate * currentPercent
    }
  }

  canvas.save()
  canvas.translate(marginX, marginY)
  drawThumbEmoji(canvas,
    size,
    faceRotate,
    isWink,
    winkRotate,
    thumbOffsetRate,
    thumbRotate,
    thumbAlpha,
    starOffsetRate,
    starAlpha
  )
}

/** emoji Thumb
 * Created by Rita on 2017/9/25.
 * <h5> emoji表情动画</h5>
 * ---------------------------------------------
 * @param canvas 画布
 * @param emojiSize <Float> emoji直径
 * ---------------------------------------------
 * @param faceRotate <Float> 旋转程度，转轴中心为emoji右边界上的中心点
 * ---------------------------------------------
 * @param isWink <Boolean> true: 眨眼，左眼眨眼状态  false: 正常，眼睛是圆的
 * @param winkRotate <Float> wink时微笑的旋转程度，转轴中心为emoji中心点
 * ---------------------------------------------
 * @param thumbOffsetRate <Float> 赞的手势位移的比例
 * @param thumbAlpha <Int> 点赞的opacity
 * @param thumbRotate <Float> 赞的旋转角度
 * ---------------------------------------------
 * @param starOffsetRate <Float> 小星星位移的比例
 * @param starAlpha <Int> 点小星星的opacity
 */
private fun drawThumbEmoji(
  canvas: Canvas,
  emojiSize: Float,
  faceRotate: Float,
  isWink: Boolean,
  winkRotate: Float,
  thumbOffsetRate: Float,
  thumbRotate: Float,
  thumbAlpha: Int,
  starOffsetRate: Float,
  starAlpha: Int) {

  // 参数
  val eyeDiameter = emojiSize * 0.15f
  val eyeMarginX = emojiSize * 0.2f
  val eyeMarginY = emojiSize * 0.35f

  val mouthDiameter = emojiSize * 0.35f
  val mouthMarginY = emojiSize * 0.55f
  val mouthAngle = 126f
  val mouthRect = RectF((emojiSize - mouthDiameter) / 2, mouthMarginY - mouthDiameter / 2,
    (emojiSize + mouthDiameter) / 2, mouthMarginY + mouthDiameter / 2
  )

  val thumbSize = emojiSize * 0.35f
  val pinkStarSize = thumbSize * 0.45f
  val blueStarSize = thumbSize * 0.25f

  val thumbOriginMarginX = emojiSize * 0.65f
  val thumbOriginMarginY = emojiSize * 0.56f
  val thumbMaxOffsetX = emojiSize * 0.1f

  val pinkStarOriginMarginX = emojiSize * 0.775f
  val pinkStarOriginMarginY = emojiSize * 0.47f
  val pinkStarMaxOffsetX = emojiSize * 0.14f
  val pinkStarMaxOffsetY = emojiSize * -0.14f

  val blueStarOriginMarginX = emojiSize * 0.75f
  val blueStarOriginMarginY = emojiSize * 0.6f
  val blueStarMaxOffsetX = emojiSize * -0.025f
  val blueStarMaxOffsetY = emojiSize * -0.065f

  val starPink = Color.argb(starAlpha, 255, 65, 110)
  val starBlue = Color.argb(starAlpha, 49, 127, 253)
  val handColor = Color.argb(thumbAlpha, 255, 144, 0)

  // 计算手与星星的margin值
  val thumbMarginX: Float = thumbOriginMarginX + thumbMaxOffsetX * thumbOffsetRate
  val thumbMarginY: Float = thumbOriginMarginY

  val pinkStarMarginX = pinkStarOriginMarginX + pinkStarMaxOffsetX * starOffsetRate
  val pinkStarMarginY = pinkStarOriginMarginY + pinkStarMaxOffsetY * starOffsetRate
  val blueStarMarginX = blueStarOriginMarginX + blueStarMaxOffsetX * starOffsetRate
  val blueStarMarginY = blueStarOriginMarginY + blueStarMaxOffsetY * starOffsetRate

  // 画脸
  drawPureFace(canvas, emojiSize)

  // 画笔
  val solidPaint = EmojiPaint.solidPaint(EmojiUI.lineColor)
  val strokePaint = EmojiPaint.emojiStrokePaint(emojiSize)

  canvas.save()
  canvas.rotate(faceRotate, emojiSize, emojiSize * 0.5f)

  // 左眼，当wink时显示wink眼睛，否则显示正常眼睛
  if (isWink) {
    canvas.rotate(winkRotate, emojiSize / 2, emojiSize / 2)
    drawEmojiWinkEye(canvas, eyeMarginX, eyeMarginY, eyeDiameter, strokePaint)

  } else {
    val rectLeftEyes = RectF(eyeMarginX, eyeMarginY, eyeMarginX + eyeDiameter,
      eyeMarginY + eyeDiameter
    )
    canvas.drawOval(rectLeftEyes, solidPaint)
  }

  // 右眼一直是正常的
  val rectRightEyes = RectF(emojiSize - eyeMarginX - eyeDiameter, eyeMarginY,
    emojiSize - eyeMarginX, eyeMarginY + eyeDiameter
  )
  canvas.drawOval(rectRightEyes, solidPaint)

  // 嘴巴
  canvas.drawArc(mouthRect, (180f - mouthAngle) / 2, mouthAngle, false, strokePaint)

  //恢复画布
  canvas.restore()
  canvas.save()

  // 画赞
  drawEmojiThumbSymbol(canvas,
    thumbSize,
    thumbMarginX,
    thumbMarginY,
    handColor,
    thumbRotate
  )

  // 画小星星
  drawFiveAngleStarSymbol(canvas,
    pinkStarSize,
    pinkStarMarginX,
    pinkStarMarginY,
    starPink,
    14.4f
  )
  drawFiveAngleStarSymbol(canvas,
    blueStarSize,
    blueStarMarginX,
    blueStarMarginY,
    starBlue,
    -21.6f
  )

  canvas.restore()
}

private fun drawEmojiThumbSymbol(
  canvas: Canvas,
  size: Float,
  marginX: Float,
  marginY: Float,
  color: Int,
  rotate: Float) {

// 曲线端点
  val A_x = size * 0.45f
  val A_y = size * 0.32f
  val B_x = size * 0.32f
  val B_y = size * 0.29f
  val C_x = size * 0.19f
  val C_y = size * 0.43f
  val D_x = size * 0.14f
  val D_y = size * 0.59f
  val E_x = size * 0.12f
  val E_y = size * 0.755f
  val F_x = size * 0.155f
  val F_y = size * 0.9f
  val G_x = size * 0.52f
  val G_y = size
  val H_x = size * 0.895f
  val H_y = size * 0.837f
  val I_x = size * 0.84f
  val I_y = size * 0.53f
  val J_x = size * 0.665f
  val J_y = size * 0.2f
  val K_x = size * 0.59f
  val K_y = 0f

  // 三阶贝塞尔控制点的坐标
  val bc1_x = size * 0.22f
  val bc1_y = size * 0.26f
  val bc2_x = size * 0.16f
  val bc2_y = size * 0.34f

  val cd1_x = size * 0.08f
  val cd1_y = size * 0.45f
  val cd2_x = size * 0.07f
  val cd2_y = size * 0.54f

  val de1_x = size * 0.04f
  val de1_y = size * 0.6f
  val de2_x = size * 0.05f
  val de2_y = size * 0.7f

  val ef1_x = size * 0.07f
  val ef1_y = size * 0.78f
  val ef2_x = size * 0.09f
  val ef2_y = size * 0.87f

  val fg1_x = size * 0.25f
  val fg1_y = size * 0.95f
  val fg2_x = size * 0.42f
  val fg2_y = size

  val gh1_x = size * 0.76f
  val gh1_y = size * 1.03f
  val gh2_x = size * 0.87f
  val gh2_y = size * 0.90f

  val hi1_x = size * 0.96f
  val hi1_y = size * 0.71f
  val hi2_x = size * 0.88f
  val hi2_y = size * 0.56f

  val ij1_x = size * 0.83f
  val ij1_y = size * 0.48f
  val ij2_x = size * 0.64f
  val ij2_y = size * 0.32f

  val jk1_x = size * 0.63f
  val jk1_y = size * 0.18f
  val jk2_x = size * 0.69f
  val jk2_y = size * 0.03f

  val ka1_x = size * 0.45f
  val ka1_y = size * 0.03f
  val ka2_x = size * 0.42f
  val ka2_y = size * 0.17f

  val path = Path()
  path.moveTo(A_x, A_y)
  path.lineTo(B_x, B_y)
  path.cubicTo(bc1_x, bc1_y, bc2_x, bc2_y, C_x, C_y)
  path.cubicTo(cd1_x, cd1_y, cd2_x, cd2_y, D_x, D_y)
  path.cubicTo(de1_x, de1_y, de2_x, de2_y, E_x, E_y)
  path.cubicTo(ef1_x, ef1_y, ef2_x, ef2_y, F_x, F_y)
  path.cubicTo(fg1_x, fg1_y, fg2_x, fg2_y, G_x, G_y)
  path.cubicTo(gh1_x, gh1_y, gh2_x, gh2_y, H_x, H_y)
  path.cubicTo(hi1_x, hi1_y, hi2_x, hi2_y, I_x, I_y)
  path.cubicTo(ij1_x, ij1_y, ij2_x, ij2_y, J_x, J_y)
  path.cubicTo(jk1_x, jk1_y, jk2_x, jk2_y, K_x, K_y)
  path.cubicTo(ka1_x, ka1_y, ka2_x, ka2_y, A_x, A_y)

  val paint = Paint()
  paint.reset()
  paint.isAntiAlias = true
  paint.color = color
  paint.strokeJoin = Paint.Join.ROUND
  paint.style = Paint.Style.FILL

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotate, size / 2f, size / 2f)
  canvas.drawPath(path, paint)

  canvas.restore()

}

fun drawFiveAngleStarSymbol(
  canvas: Canvas,
  size: Float,
  marginX: Float,
  marginY: Float,
  color: Int,
  rotate: Float) {

  val path = multipleAngleStarPath(5, size)
  val paint = Paint()
  paint.reset()
  paint.isAntiAlias = true
  paint.color = color
  paint.strokeJoin = Paint.Join.ROUND
  paint.style = Paint.Style.FILL

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotate, size / 2f, size / 2f)
  canvas.drawPath(path, paint)

  canvas.restore()

}

private fun multipleAngleStarPath(angleCount: Int, size: Float): Path {
  val singleAngle: Double = Math.PI * 2 / angleCount.toDouble()
  val pointRadiusAngle: Double = angleToRadian(2f)
  val radius: Float = size / 2
  val innerRadius: Float = size * 0.25f
  val pointRadiusRadius: Float = size * 0.47f

  val path = Path()

  var angle = -Math.PI / 2
  for (index in 0 until angleCount) {

    val innerAngle = singleAngle / 2 + angle
    val pointAngle_1 = -pointRadiusAngle + angle
    val pointAngle_2 = pointRadiusAngle + angle

    val pointX: Float = radius + radius * Math.cos(angle).toFloat()
    val pointY: Float = radius + radius * Math.sin(angle).toFloat()

    val pointRadius_1X: Float = radius + pointRadiusRadius * Math.cos(pointAngle_1).toFloat()
    val pointRadius_1Y: Float = radius + pointRadiusRadius * Math.sin(pointAngle_1).toFloat()

    val pointRadius_2X: Float = radius + pointRadiusRadius * Math.cos(pointAngle_2).toFloat()
    val pointRadius_2Y: Float = radius + pointRadiusRadius * Math.sin(pointAngle_2).toFloat()

    val innerPointX: Float = radius + innerRadius * Math.cos(innerAngle).toFloat()
    val innerPointY: Float = radius + innerRadius * Math.sin(innerAngle).toFloat()

    if (index == 0) {
      path.moveTo(pointRadius_1X, pointRadius_1Y)
    } else {
      path.lineTo(pointRadius_1X, pointRadius_1Y)
    }
    path.quadTo(pointX, pointY, pointRadius_2X, pointRadius_2Y)
    path.lineTo(innerPointX, innerPointY)

    angle += singleAngle
  }
  return path
}

private fun drawEmojiWinkEye(canvas: Canvas, marginX: Float, marginY: Float, size: Float, paint: Paint) {

  val path = emojiWinkEyePath(size)

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.drawPath(path, paint)
  canvas.restore()
}

private fun emojiWinkEyePath(size: Float): Path {
  val path = Path()
  val angle: Double = angleToRadian(55f)
  val middleRadius: Float = size * 0.9f
  val innerRadius: Float = size * 0.3f

  path.moveTo(size * 1.1f - middleRadius * Math.cos(angle / 2f).toFloat(),
    size / 2f - middleRadius * Math.sin(angle / 2).toFloat()
  )
  path.lineTo(size * 1.1f - middleRadius * Math.cos(angle / 2f).toFloat(),
    size / 2f - middleRadius * Math.sin(angle / 2).toFloat()
  )
  path.quadTo(size * 1.1f,
    size / 2f,
    size * 1.1f - innerRadius * Math.cos(angle / 2).toFloat(),
    size / 2f + innerRadius * Math.sin(angle / 2).toFloat()
  )
  path.lineTo(size * 1.1f - middleRadius * Math.cos(angle / 2f).toFloat(),
    size / 2f + middleRadius * Math.sin(angle / 2).toFloat()
  )

  return path
}