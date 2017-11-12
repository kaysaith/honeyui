package com.blinnnk.emoji

import android.graphics.*

/**
 * @date 24/10/2017
 * @author KaySaith
 */

fun loadSpeechlessEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  marginX: Float,
  marginY: Float,
  size: Float,
  isAnimate: Boolean) {
  /// 关键时间点
  val mainKeyTimes = floatArrayOf(0f, 0.3f, 0.4f, 0.6f, 0.7f, 1f)
  val proceed: Float = currentTime / duration % 1f

  val baseOffsetUpX = size * 0.05f
  val baseOffsetUpY = size * -0.05f
  val baseOffsetDownX = size * 0.02f
  val baseOffsetDownY = size * 0.02f
  val handWaveUpRotation = 8f
  val handWaveDownRotation: Float = -2f

  var baseOffsetX = 0f
  var baseOffsetY = 0f
  var handWaveRation = 0f
  var faceWaveRate = 0f

  if (isAnimate) {

    faceWaveRate = Math.sin(proceed * Math.PI * 2 * 8).toFloat()

    if (proceed < mainKeyTimes[1]) {
      //【start ~ 1】
      val currentPercent = proceed / mainKeyTimes[1]
      baseOffsetX = baseOffsetUpX * currentPercent
      baseOffsetY = baseOffsetUpY * currentPercent
      handWaveRation = handWaveUpRotation * currentPercent

    } else if (proceed >= mainKeyTimes[1] && proceed < mainKeyTimes[2]) {
      //【1 ~ 2】
      baseOffsetX = baseOffsetUpX
      baseOffsetY = baseOffsetUpY
      handWaveRation = handWaveUpRotation

    } else if (proceed >= mainKeyTimes[2] && proceed < mainKeyTimes[3]) {
      //【2 ~ 3】
      val currentPercent = (proceed - mainKeyTimes[2]) / (mainKeyTimes[3] - mainKeyTimes[2])
      baseOffsetX = baseOffsetUpX + (baseOffsetDownX - baseOffsetUpX) * currentPercent
      baseOffsetY = baseOffsetUpY + (baseOffsetDownY - baseOffsetUpY) * currentPercent
      handWaveRation = handWaveUpRotation + (handWaveDownRotation - handWaveUpRotation) * currentPercent

    } else if (proceed >= mainKeyTimes[3] && proceed < mainKeyTimes[4]) {
      //【3 ~ 4】
      baseOffsetX = baseOffsetDownX
      baseOffsetY = baseOffsetDownY
      handWaveRation = handWaveDownRotation

    } else if (proceed >= mainKeyTimes[4] && proceed < 1f) {
      //【4 ~ end】
      val currentPercent = (proceed - mainKeyTimes[4]) / (1f - mainKeyTimes[4])
      baseOffsetX = baseOffsetDownX * (1f - currentPercent)
      baseOffsetY = baseOffsetDownY * (1f - currentPercent)
      handWaveRation = handWaveDownRotation * (1f - currentPercent)

    }
  }

  canvas.save()
  canvas.translate(marginX, marginY)
  drawSpeechlessEmoji(canvas, size, baseOffsetX, baseOffsetY, faceWaveRate, handWaveRation)
  canvas.restore()
}

private fun drawSpeechlessEmoji(
  canvas: Canvas,
  emojiSize: Float,
  baseOffsetX: Float,
  baseOffsetY: Float,
  faceWaveRate: Float,
  handRotation: Float) {

  val eyeDiameter = emojiSize * 0.2f
  val eyeAngle = 90f
  val eyeMarginX = emojiSize * 0.14f
  val eyeMarginY = emojiSize * 0.45f

  val mouthMarginY = emojiSize * 0.66f
  val mouthWidth = emojiSize * 0.45f
  val mouthHeight = emojiSize * 0.15f
  val rotate = 30f

  val handSize = emojiSize * 0.65f
  val handMarginX = emojiSize * 0.34f
  val handMarginY = emojiSize * 0.2f

  val tearWidth = emojiSize * 0.1f
  val tearMarginX = emojiSize * 0.21f
  val tearRotation = 15f
  val faceWaveX = emojiSize * 0.002f

  val facePath = Path()
  val rectF = RectF(0f, 0f, emojiSize, emojiSize)
  facePath.addOval(rectF, Path.Direction.CW)

  // 准备位置
  val leftEyeRect = RectF(eyeMarginX,
    eyeMarginY,
    eyeMarginX + eyeDiameter,
    eyeMarginY + eyeDiameter
  )
  val eyePaint = EmojiPaint.emojiStrokePaint(emojiSize)

  canvas.save()
  // 画脸
  drawPureFace(canvas, emojiSize)
  canvas.clipPath(facePath)  // 沿着脸的边缘裁切画布

  // 旋转画布，画倾斜的眼睛、眼泪、嘴巴
  canvas.rotate(rotate, emojiSize / 2f, emojiSize / 2f)
  canvas.translate(baseOffsetX * 1.2f + faceWaveRate * faceWaveX, baseOffsetY * 1.2f)

  // 嘴巴
  drawEmojiOpenedMouth(canvas,
    MouthType.Flat,
    (emojiSize - mouthWidth) / 2f,
    mouthMarginY,
    mouthWidth,
    mouthHeight,
    0f
  )

  // 眼泪
  canvas.translate(baseOffsetX * 0.5f, baseOffsetY * 0.5f)
  drawEmojiTear(canvas,
    tearMarginX,
    eyeMarginY,
    tearWidth,
    emojiSize,
    tearRotation
  )

  drawEmojiTear(canvas,
    emojiSize - tearMarginX - tearWidth,
    eyeMarginY,
    tearWidth,
    emojiSize,
    -tearRotation
  )
  canvas.drawArc(leftEyeRect, 280f - eyeAngle / 2f, eyeAngle, false, eyePaint)

  // 手
  canvas.restore() // 去除掉clip
  canvas.rotate(rotate, emojiSize / 2f, emojiSize / 2f)
  canvas.translate(baseOffsetX * 1.8f + faceWaveRate * faceWaveX, baseOffsetY * 2f)
  drawEmojiWaveHandSymbol(canvas, handSize, handMarginX, handMarginY, EmojiUI.handColor,
    -rotate + handRotation
  )

}

private fun drawEmojiWaveHandSymbol(
  canvas: Canvas,
  size: Float,
  marginX: Float,
  marginY: Float,
  color: Int,
  rotate: Float) {

  // 曲线端点
  val A_x: Float = size * 0.142f
  val A_y: Float = size * 0.119f
  val B_x: Float = size * 0.172f
  val B_y: Float = size * 0.426f
  val C_x: Float = size * 0.373f
  val C_y: Float = size * 0.562f
  val D_x: Float = size * 0.539f
  val D_y: Float = size * 0.758f
  val E_x: Float = size * 0.735f
  val E_y: Float = size
  val F_x: Float = size * 0.881f
  val F_y: Float = size * 0.663f
  val G_x: Float = size * 0.73f
  val G_y: Float = size * 0.15f
  val H_x: Float = size * 0.6f
  val H_y: Float = size * 0.07f
  val I_x: Float = size * 0.58f
  val I_y: Float = size * 0.15f
  val J_x: Float = size * 0.43f
  val J_y: Float = size * 0.038f

  // 三阶贝塞尔控制点的坐标
  val ab1_x: Float = size * 0.09f
  val ab1_y: Float = size * 0.26f
  val ab2_x: Float = size * 0.13f
  val ab2_y: Float = size * 0.38f

  val bc_x: Float = size * 0.28f
  val bc_y: Float = size * 0.54f

  val cd1_x: Float = size * 0.53f
  val cd1_y: Float = size * 0.6f
  val cd2_x: Float = size * 0.53f
  val cd2_y: Float = size * 0.6f

  val de1_x: Float = size * 0.54f
  val de1_y: Float = size * 0.78f
  val de2_x: Float = size * 0.54f
  val de2_y: Float = size

  val ef1_x: Float = size * 0.88f
  val ef1_y: Float = size * 0.99f
  val ef2_x: Float = size * 0.93f
  val ef2_y: Float = size * 0.92f

  val fg1_x: Float = size * 0.82f
  val fg1_y: Float = size * 0.36f
  val fg2_x: Float = size * 0.78f
  val fg2_y: Float = size * 0.24f

  val gh1_x: Float = size * 0.69f
  val gh1_y: Float = size * 0.08f
  val gh2_x: Float = size * 0.67f
  val gh2_y: Float = size * 0.06f

  val hi_x: Float = size * 0.56f
  val hi_y: Float = size * 0.09f

  val ij_x: Float = size * 0.50f
  val ij_y: Float = size * 0.09f

  val ja1_x: Float = size * 0.33f
  val ja1_y: Float = size * -0.02f
  val ja2_x: Float = size * 0.17f
  val ja2_y: Float = size * -0.03f

  val path = Path()
  path.moveTo(A_x, A_y)
  path.cubicTo(ab1_x, ab1_y, ab2_x, ab2_y, B_x, B_y)
  path.quadTo(bc_x, bc_y, C_x, C_y)
  path.cubicTo(cd1_x, cd1_y, cd2_x, cd2_y, D_x, D_y)
  path.cubicTo(de1_x, de1_y, de2_x, de2_y, E_x, E_y)
  path.cubicTo(ef1_x, ef1_y, ef2_x, ef2_y, F_x, F_y)
  path.cubicTo(fg1_x, fg1_y, fg2_x, fg2_y, G_x, G_y)
  path.cubicTo(gh1_x, gh1_y, gh2_x, gh2_y, H_x, H_y)
  path.quadTo(hi_x, hi_y, I_x, I_y)
  path.quadTo(ij_x, ij_y, J_x, J_y)
  path.cubicTo(ja1_x, ja1_y, ja2_x, ja2_y, A_x, A_y)

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

private fun drawEmojiTear(canvas: Canvas, marginX: Float, marginY: Float, width: Float, emojiSize: Float, rotation: Float) {

  val paint = EmojiPaint.solidPaint(EmojiUI.tearColor)

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotation, width / 2f, 0f)
  canvas.drawRect(0f, 0f, width, emojiSize, paint)
  canvas.restore()
}

enum class MouthType {
  Oval, Flat
}

fun drawEmojiOpenedMouth(
  canvas: Canvas,
  mouthType: MouthType,
  marginX: Float,
  marginY: Float,
  width: Float,
  height: Float,
  rotate: Float) {
  val mouthPath = Path()
  when (mouthType) {
    MouthType.Oval -> {
      val rectF = RectF(0f, 0f, width, height)
      mouthPath.addOval(rectF, Path.Direction.CW)
    }

    MouthType.Flat -> {

      val A_x: Float = width / 2f
      val A_y = 0f
      val B_x: Float = width
      val B_y: Float = height / 6f
      val C_x: Float = width / 2f
      val C_y: Float = height
      val D_x = 0f
      val D_y: Float = height / 6f

      val ab1_x: Float = width * 0.76f
      val ab1_y = 0f
      val ab2_x: Float = width
      val ab2_y: Float = -height / 6f

      val bc1_x: Float = width
      val bc1_y: Float = height / 2f
      val bc2_x: Float = width * 0.77f
      val bc2_y: Float = height

      val cd1_x: Float = width * 0.23f
      val cd1_y: Float = height
      val cd2_x = 0f
      val cd2_y: Float = height / 2f

      val da1_x = 0f
      val da1_y: Float = -height / 6f
      val da2_x: Float = width * 0.27f
      val da2_y = 0f

      mouthPath.moveTo(A_x, A_y)
      mouthPath.cubicTo(ab1_x, ab1_y, ab2_x, ab2_y, B_x, B_y)
      mouthPath.cubicTo(bc1_x, bc1_y, bc2_x, bc2_y, C_x, C_y)
      mouthPath.cubicTo(cd1_x, cd1_y, cd2_x, cd2_y, D_x, D_y)
      mouthPath.cubicTo(da1_x, da1_y, da2_x, da2_y, A_x, A_y)
    }
  }
  val teethWidth = width * 0.75f
  val teethHeight = height * 0.3f

  val tongueMarginY = height * 0.45f
  val tongueWidth = width * 0.9f
  val tongueHeight = height * 0.86f

  val teethPaint = EmojiPaint.solidPaint(Color.WHITE)
  val tonguePaint = EmojiPaint.solidPaint(EmojiUI.tongueColor)
  val mouthPaint = EmojiPaint.solidPaint(EmojiUI.lineColor)

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotate, width / 2f, height / 2f)

  // 画嘴巴
  canvas.drawPath(mouthPath, mouthPaint)
  canvas.clipPath(mouthPath)

  // 画牙齿
  val rectFTooth = RectF((width - teethWidth) / 2, -teethHeight / 2,
    (width + teethWidth) / 2, teethHeight / 2
  )
  canvas.drawRoundRect(rectFTooth,
    teethHeight / 2, teethHeight / 2, teethPaint
  )

  // 画舌头
  val rectF = RectF((width - tongueWidth) / 2f, tongueMarginY, (width + tongueWidth) / 2f,
    tongueMarginY + tongueHeight
  )
  canvas.drawOval(rectF, tonguePaint)
  canvas.restore()

}