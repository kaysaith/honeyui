package com.blinnnk.emoji

import android.graphics.*

/**
 * @date 24/10/2017
 * @author Rita
 */

fun loadSobEmoji(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  marginX: Float,
  marginY: Float,
  size: Float,
  isAnimate: Boolean) {
  /// 关键时间点
  val proceed: Float = currentTime / duration % 1f
  val dropProceed: Float = currentTime / (duration / 2f) % 1f  // 眼泪的周期是duration的1/2
  val mainKeyTimes = floatArrayOf(0f, 0.1f, 0.5f, 0.6f, 0.66f, 0.74f, 0.79f, 0.84f, 1f)
  val eyeScaleKeyTimes = floatArrayOf(0f, 0.2f, 0.225f, 0.25f, 0.7f, 0.725f, 0.75f, 1f)
  val dropKeyTimes = floatArrayOf(0f, 0.2f, 0.35f, 0.5f, 0.7f, 0.85f, 1f)

  val mouthAnimateArcNormalRate = 0.85f
  val mouthAnimateArcMinRate = 0.6f
  val eyeAnimateScaleMaxRate = 0.1f

  var faceOffsetX = 0f
  var faceOffsetY = 0f
  var eyeAnimateScaleRate = 1f
  var mouthAnimateArcRate = 1f
  var leftDropScaleAnimateRate = 0f
  var leftDropAlphaAnimateRate = 1f
  var leftDropOffsetAnimateRate = 0f
  var rightDropScaleAnimateRate = 1f
  var rightDropOffsetAnimateRate = 0f
  var rightDropAlphaAnimateRate = 1f

  if (isAnimate) {

    // 主要动画，这里包含了： 1. 整体脸的位移变化  2. 眼睛的变化  3. 嘴巴的弧度变化
    if (proceed < mainKeyTimes[1]) {
      // 【Start~1】
      val currentPercent = proceed / mainKeyTimes[1]
      mouthAnimateArcRate = 1f - (1f - mouthAnimateArcMinRate) * currentPercent

    } else if (proceed < mainKeyTimes[3]) {

      mouthAnimateArcRate = if (proceed < mainKeyTimes[2]) {
        // 【1 ~ 2】
        mouthAnimateArcMinRate

      } else {
        // 【2 ~ 3】
        val currentPercent = (proceed - mainKeyTimes[2]) / (mainKeyTimes[3] - mainKeyTimes[2])
        mouthAnimateArcMinRate + (mouthAnimateArcNormalRate - mouthAnimateArcMinRate) * currentPercent
      }

      // 【1 ~ 3】
      val currentPercent = (proceed - mainKeyTimes[1]) / (mainKeyTimes[3] - mainKeyTimes[1])
      faceOffsetY = currentPercent

    } else {

      // 【 > 3】
      when {
        proceed < mainKeyTimes[4] -> {
          // 【3 ~ 4】在0.6f到0.66f之间
          val currentPercent = (proceed - mainKeyTimes[3]) / (mainKeyTimes[4] - mainKeyTimes[3])
          faceOffsetX = -currentPercent

        }

        proceed < mainKeyTimes[6] -> {
          // 【4 ~ 6】在0.66f到0.76f之间
          val currentPercent = (proceed - mainKeyTimes[4]) / (mainKeyTimes[6] - mainKeyTimes[4])
          faceOffsetX = 2 * currentPercent - 1f

        }

        proceed < mainKeyTimes[7] -> {
          // 【6 ~ 7】
          val invertedPercent = (mainKeyTimes[7] - proceed) / (mainKeyTimes[7] - mainKeyTimes[6])
          faceOffsetX = invertedPercent

        }

        else -> {

          // 【7 ~ end】
          val currentPercent = (proceed - mainKeyTimes[7]) / (1f - mainKeyTimes[7])
          faceOffsetY = 1f - currentPercent
          mouthAnimateArcRate = 1f

        }
      }

      if (proceed < mainKeyTimes[7]) {
        // 【3 ~ 7】 mouth -max
        val currentPercent = (proceed - mainKeyTimes[3]) / (mainKeyTimes[7] - mainKeyTimes[3])
        mouthAnimateArcRate = mouthAnimateArcNormalRate + (1f - mouthAnimateArcNormalRate) * currentPercent
        faceOffsetY = 1f

      }

    }

    // 眼睛眨动的动画
    if (proceed >= eyeScaleKeyTimes[1] && proceed < eyeScaleKeyTimes[2]) {
      //变小
      val currentPercent = (proceed - eyeScaleKeyTimes[1]) / (eyeScaleKeyTimes[2] - eyeScaleKeyTimes[1])
      eyeAnimateScaleRate = 1f - (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else if (proceed >= eyeScaleKeyTimes[2] && proceed < eyeScaleKeyTimes[3]) {
      //恢复
      val currentPercent = (proceed - eyeScaleKeyTimes[2]) / (eyeScaleKeyTimes[3] - eyeScaleKeyTimes[2])
      eyeAnimateScaleRate = eyeAnimateScaleMaxRate + (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else if (proceed > eyeScaleKeyTimes[4] && proceed < eyeScaleKeyTimes[5]) {
      //变小
      val currentPercent = (proceed - eyeScaleKeyTimes[4]) / (eyeScaleKeyTimes[5] - eyeScaleKeyTimes[4])
      eyeAnimateScaleRate = 1f - (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else if (proceed > eyeScaleKeyTimes[5] && proceed < eyeScaleKeyTimes[6]) {
      //恢复
      val currentPercent = (proceed - eyeScaleKeyTimes[5]) / (eyeScaleKeyTimes[6] - eyeScaleKeyTimes[5])
      eyeAnimateScaleRate = eyeAnimateScaleMaxRate + (1f - eyeAnimateScaleMaxRate) * currentPercent

    } else {
      eyeAnimateScaleRate = 1f
    }

    // 眼泪的动画
    if (dropProceed < dropKeyTimes[2]) {
      if (dropProceed >= dropKeyTimes[1] && dropProceed < dropKeyTimes[2]) {
        // 【1 ~ 2】
        val currentPercent = (dropProceed - dropKeyTimes[1]) / (dropKeyTimes[2] - dropKeyTimes[1])
        rightDropAlphaAnimateRate = 1f - currentPercent

      }

      val currentPercent = dropProceed / dropKeyTimes[2]
      rightDropOffsetAnimateRate = currentPercent

    } else if (dropProceed >= dropKeyTimes[2] && dropProceed < dropKeyTimes[3]) {
      // 【2 ~ 3】
      val currentPercent = (dropProceed - dropKeyTimes[2]) / (dropKeyTimes[3] - dropKeyTimes[2])
      rightDropAlphaAnimateRate = 0f
      leftDropScaleAnimateRate = currentPercent
      leftDropAlphaAnimateRate = 1f
    } else if (dropProceed >= dropKeyTimes[3] && dropProceed < dropKeyTimes[5]) {
      //【3 ~ 5】

      if (dropProceed >= dropKeyTimes[3] && dropProceed < dropKeyTimes[4]) {
        // 【3 ~ 4】
        rightDropAlphaAnimateRate = 0f
        leftDropScaleAnimateRate = 1f
        leftDropAlphaAnimateRate = 1f

      } else {
        // 【4 ~ 5】
        val currentPercent = (dropProceed - dropKeyTimes[4]) / (dropKeyTimes[5] - dropKeyTimes[4])
        rightDropAlphaAnimateRate = 0f
        leftDropScaleAnimateRate = 1f
        leftDropAlphaAnimateRate = 1f - currentPercent
      }

      val currentPercent = (dropProceed - dropKeyTimes[3]) / (dropKeyTimes[5] - dropKeyTimes[3])
      leftDropOffsetAnimateRate = currentPercent

    } else if (dropProceed >= dropKeyTimes[5] && dropProceed < 1f) {
      // 【5 ~ End】
      val currentPercent = (dropProceed - dropKeyTimes[5]) / (1f - dropKeyTimes[5])
      rightDropScaleAnimateRate = currentPercent
    }

  }

  canvas.save()
  canvas.translate(marginX, marginY)
  drawSobEmoji(canvas,
    size,
    faceOffsetX,
    faceOffsetY,
    eyeAnimateScaleRate,
    mouthAnimateArcRate,
    leftDropScaleAnimateRate,
    leftDropOffsetAnimateRate,
    leftDropAlphaAnimateRate,
    rightDropScaleAnimateRate,
    rightDropOffsetAnimateRate,
    rightDropAlphaAnimateRate
  )
  canvas.restore()
}

/** emoji Sob 啜泣
 * Created by Rita on 2017/9/25.
 * <h5> emoji Sob 啜泣表情动画</h5>
 * ---------------------------------------------
 * @param canvas 画布
 * @param emojiSize <Float> emoji直径
 * ---------------------------------------------
 * @param faceOffsetRateX <Float> 偏移程度，控制脸部五官的左右运动，取值范围: [0-1]
 * @param faceOffsetRateY <Float> 偏移程度，控制脸部五官的上下运动，取值范围：[0-1]
 * ---------------------------------------------
 * @param eyeAnimateScaleRate <Float> 眨眼时，眼睛height的形变比例，取值范围：[0-1f]
 * @param mouthAnimateArcRate <Float> 嘴巴的弧度形变的比例，取值范围：[0-1f]
 * ---------------------------------------------
 * @param leftDropScaleAnimateRate <Float> 左泪滴的形变程度，取值范围：[0-1f]
 * @param leftDropOffsetAnimateRate <Float> 左泪滴的偏移程度，取值范围：[0-1f]
 * @param leftDropAlphaAnimateRate <Float> 左泪滴的透明程度，取值范围：[0-1f]
 * @param rightDropScaleAnimateRate <Float> 右泪滴的形变程度，取值范围：[0-1f]
 * @param rightDropOffsetAnimateRate <Float> 右泪滴的偏移程度，取值范围：[0-1f]
 * @param rightDropAlphaAnimateRate <Float> 右泪滴的透明程度，取值范围：[0-1f]
 */
private fun drawSobEmoji(
  canvas: Canvas,
  emojiSize: Float,
  faceOffsetRateX: Float,
  faceOffsetRateY: Float,
  eyeAnimateScaleRate: Float,
  mouthAnimateArcRate: Float,
  leftDropScaleAnimateRate: Float,
  leftDropOffsetAnimateRate: Float,
  leftDropAlphaAnimateRate: Float,
  rightDropScaleAnimateRate: Float,
  rightDropOffsetAnimateRate: Float,
  rightDropAlphaAnimateRate: Float) {

  // 嘴巴的参数
  val mouthArc = 120f * mouthAnimateArcRate //嘴巴的最大弧度
  val mouthRadius = emojiSize * 0.14f
  val mouthMarginY = emojiSize * 0.67f

  // 眼睛的参数
  val eyeRadius = emojiSize * 0.075f
  val eyeLeftCenterX = emojiSize * 0.28f
  val eyeRightCenterX = emojiSize * 0.72f
  val eyeCenterY = emojiSize * 0.5f

  // 眼泪的参数
  val dropWidth = emojiSize * 0.15f
  val dropHeight = emojiSize * 0.25f
  val dropMarginX = emojiSize * 0.14f
  val dropMarginY = emojiSize * 0.57f
  val dropMaxOffsetX = emojiSize * 0.02f
  val dropMaxOffsetY = emojiSize * 0.06f
  val dropRotate = 18f

  val faceOffsetMaxX = emojiSize * 0.03f
  val faceOffsetMaxY = emojiSize * 0.08f

  // 准备嘴巴和眼睛的坐标
  val mouthRect = RectF(emojiSize / 2 - mouthRadius,
    mouthMarginY,
    emojiSize / 2 + mouthRadius,
    mouthMarginY + mouthRadius * 2
  )
  val eyeLeftRect = RectF(eyeLeftCenterX - eyeRadius, eyeCenterY - eyeRadius * eyeAnimateScaleRate,
    eyeLeftCenterX + eyeRadius, eyeCenterY + eyeRadius * eyeAnimateScaleRate
  )
  val eyeRightRect = RectF(eyeRightCenterX - eyeRadius,
    eyeCenterY - eyeRadius * eyeAnimateScaleRate, eyeRightCenterX + eyeRadius,
    eyeCenterY + eyeRadius * eyeAnimateScaleRate
  )

  // 准备泪滴的坐标
  val leftDropAlpha = (255 * leftDropAlphaAnimateRate).toInt()
  val rightDropAlpha = (255 * rightDropAlphaAnimateRate).toInt()

  // 眼泪的色值
  val leftDropColor = Color.argb(leftDropAlpha, 68, 191, 230)
  val righttDropColor = Color.argb(rightDropAlpha, 68, 191, 230)

  // 开始绘制
  val solidPaint = EmojiPaint.solidPaint(EmojiUI.lineColor)
  val linePaint = EmojiPaint.emojiStrokePaint(emojiSize)

  // 绘制渐变底色的脸
  canvas.save()
  drawPureFace(canvas, emojiSize)

  // 脸部上下运动的动画
  canvas.translate(faceOffsetMaxX * faceOffsetRateX, faceOffsetMaxY * faceOffsetRateY)

  // 绘制眼睛
  canvas.drawOval(eyeLeftRect, solidPaint)
  canvas.drawOval(eyeRightRect, solidPaint)

  // 绘制嘴巴
  canvas.drawArc(mouthRect, 180f + (180f - mouthArc) / 2, mouthArc, false, linePaint)

  // 绘制泪滴
  drawEmojiDropSymbol(canvas,
    dropMarginX - dropMaxOffsetX * leftDropOffsetAnimateRate,
    dropMarginY + dropMaxOffsetY * leftDropOffsetAnimateRate,
    dropWidth,
    dropHeight,
    leftDropColor,
    leftDropScaleAnimateRate,
    dropRotate
  )

  drawEmojiDropSymbol(canvas,
    emojiSize - dropMarginX - dropWidth + dropMaxOffsetX * rightDropOffsetAnimateRate,
    dropMarginY + dropMaxOffsetY * rightDropOffsetAnimateRate,
    dropWidth,
    dropHeight,
    righttDropColor,
    rightDropScaleAnimateRate,
    -dropRotate
  )

  canvas.restore()

}

private fun drawEmojiDropSymbol(
  canvas: Canvas,
  marginX: Float,
  marginY: Float,
  width: Float,
  height: Float,
  color: Int,
  scale: Float,
  rotate: Float) {

  val path = emojiDropPath(width, height)
  val paint = Paint()
  paint.reset()
  paint.isAntiAlias = true
  paint.color = color
  paint.style = Paint.Style.FILL

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotate, width / 2f, 0f)
  canvas.scale(scale, scale, width / 2f, 0f)
  canvas.drawPath(path, paint)

  canvas.restore()
}

private fun emojiDropPath(width: Float, height: Float): Path {

  val arcRadius = width / 2f
  val arcRect = RectF(0f, height - width, width, height)
  val startRadian: Double = -Math.asin(arcRadius / (height - arcRadius).toDouble())
  val startAngle: Float = 180f * (startRadian / Math.PI).toFloat()
  val sweepAngle: Float = 180f - 2 * startAngle

  val path = Path()
  path.addArc(arcRect, startAngle, sweepAngle)
  path.lineTo(width / 2f, 0f)
  path.close()

  return path
}