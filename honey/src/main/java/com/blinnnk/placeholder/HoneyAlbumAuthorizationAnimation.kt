package com.blinnnk.placeholder

import android.graphics.*
import com.blinnnk.emoji.EmojiPaint
import com.blinnnk.emoji.EmojiUI
import com.blinnnk.uikit.HoneyColor
import com.blinnnk.util.angleToRadian

/**
 * @date 29/10/2017 1:28 PM
 * @author Rita
 */

enum class AuthorizationAnimationStyle(var type: Int) {
  Once(0), Repeat(1), Continuous(2)
}

fun loadAlbumAuthorizationAnimation(
  canvas: Canvas,
  currentTime: Float,
  duration: Float,
  size: Float,
  isAnimate: Boolean,
  animationType: AuthorizationAnimationStyle) {

  val bottomPhotoNormalRotation = -15f
  var photoScale = 1f
  var bottomPhotoRotation = bottomPhotoNormalRotation
  var picShapeOffsetRate = 0f
  var wheelScale = 1f
  var wheelRotation = 0f

  var mainProceed = currentTime / duration

  // 关键时间点
  when (animationType) {
    AuthorizationAnimationStyle.Once ->if (mainProceed > 1f) { mainProceed = 1f }
    AuthorizationAnimationStyle.Repeat -> mainProceed = (currentTime / duration) % 1f
    AuthorizationAnimationStyle.Continuous -> mainProceed = currentTime / duration
  }

  if (isAnimate) {

    val mainKeyTimes = floatArrayOf(0f, 0.085f, 0.125f, 0.14f, 0.15f, 0.175f, 0.25f, 0.26f, 1f)
    val wheelKeyTimes = floatArrayOf(0f, 0.23f, 0.3f, 0.315f, 0.33f, 1f)

    val photoMinScaleRate = 0.8f
    val bottomPhotoMaxRotation = -20f
    val bottomPhotoMinRotation = -10f
    val picShapeOffsetOriginRate = 0.62f //出现前的状态
    val picShapeOffsetMaxRate = -0.08f //最高点
    val picShapeOffsetMinRate = 0.04f //往下抖动的点
    val wheelMaxScale = 1.2f
    val wheelMinScale = 0.8f

    // 主要动画，这里包含了： 两张图片的出现、抖动、彩色图片内山的形状的移动
    if (mainProceed < mainKeyTimes[1]) {
      // 【start ~ 1】
      val currentPercent = mainProceed / mainKeyTimes[1]
      photoScale = currentPercent * 1f
      bottomPhotoRotation = 0f
      picShapeOffsetRate = picShapeOffsetOriginRate

    } else {

      if (mainProceed >= mainKeyTimes[1] && mainProceed < mainKeyTimes[3]) {

        if (mainProceed >= mainKeyTimes[1] && mainProceed < mainKeyTimes[2]) {
          // 【1 ~ 2】
          val currentPercent = (mainProceed - mainKeyTimes[1]) / (mainKeyTimes[2] - mainKeyTimes[1])
          photoScale = 1f - (1f - photoMinScaleRate) * currentPercent
          picShapeOffsetRate = picShapeOffsetOriginRate

        } else if (mainProceed >= mainKeyTimes[2] && mainProceed < mainKeyTimes[3]) {
          // 【2 ~ 3】
          val currentPercent = (mainProceed - mainKeyTimes[2]) / (mainKeyTimes[3] - mainKeyTimes[2])
          photoScale = photoMinScaleRate + (1f - photoMinScaleRate) * currentPercent
        }

        //【1 ~ 3】
        val currentPercent = (mainProceed - mainKeyTimes[1]) / (mainKeyTimes[3] - mainKeyTimes[1])
        bottomPhotoRotation = currentPercent * bottomPhotoMaxRotation

      } else if (mainProceed >= mainKeyTimes[3] && mainProceed < mainKeyTimes[4]) {
        //【3 ~ 4】
        val currentPercent = (mainProceed - mainKeyTimes[3]) / (mainKeyTimes[4] - mainKeyTimes[3])
        bottomPhotoRotation = bottomPhotoMaxRotation - (bottomPhotoMaxRotation - bottomPhotoMinRotation) * currentPercent

      } else if (mainProceed >= mainKeyTimes[4] && mainProceed < mainKeyTimes[5]) {
        //【4 ~ 5】
        val currentPercent = (mainProceed - mainKeyTimes[4]) / (mainKeyTimes[5] - mainKeyTimes[4])
        bottomPhotoRotation = bottomPhotoMinRotation + (bottomPhotoNormalRotation - bottomPhotoMinRotation) * currentPercent

      } else if (mainProceed >= mainKeyTimes[5] && mainProceed < mainKeyTimes[6]) {
        //【5 ~ 6】
        val currentPercent = (mainProceed - mainKeyTimes[5]) / (mainKeyTimes[6] - mainKeyTimes[5])
        picShapeOffsetRate = picShapeOffsetMaxRate - (picShapeOffsetMaxRate - picShapeOffsetMinRate) * currentPercent

      } else if (mainProceed >= mainKeyTimes[6] && mainProceed < mainKeyTimes[7]) {
        //【6 ~ 7】
        val currentPercent = (mainProceed - mainKeyTimes[6]) / (mainKeyTimes[7] - mainKeyTimes[6])
        picShapeOffsetRate = picShapeOffsetMinRate * (1 - currentPercent)
      }

      if (mainProceed >= mainKeyTimes[2] && mainProceed < mainKeyTimes[5]) {
        //【2 ~ 5】
        val currentPercent = (mainProceed - mainKeyTimes[2]) / (mainKeyTimes[5] - mainKeyTimes[2])
        picShapeOffsetRate = picShapeOffsetOriginRate + (picShapeOffsetMaxRate - picShapeOffsetOriginRate) * currentPercent
      }


    }

    // 设置按钮动画，设置按钮动画会一直循环
    if (mainProceed < wheelKeyTimes[1]) {
      //【start ~ 1】
      wheelScale = 0f
    } else if (mainProceed >= wheelKeyTimes[1] && mainProceed < wheelKeyTimes[2]) {
      // 【1 ~ 2】
      val currentPercent = (mainProceed - wheelKeyTimes[1]) / (wheelKeyTimes[2] - wheelKeyTimes[1])
      wheelScale = currentPercent * wheelMaxScale

    } else if (mainProceed >= wheelKeyTimes[2] && mainProceed < wheelKeyTimes[3]) {
      // 【2 ~ 3】
      val currentPercent = (mainProceed - wheelKeyTimes[2]) / (wheelKeyTimes[3] - wheelKeyTimes[2])
      wheelScale = wheelMaxScale - currentPercent * (wheelMaxScale - wheelMinScale)

    } else if (mainProceed >= wheelKeyTimes[3] && mainProceed < wheelKeyTimes[4]) {
      // 【3 ~ 4】
      val currentPercent = (mainProceed - wheelKeyTimes[3]) / (wheelKeyTimes[4] - wheelKeyTimes[3])
      wheelScale = wheelMinScale + currentPercent * (1f - wheelMinScale)
    }

    if (mainProceed >= wheelKeyTimes[4]) {
      wheelRotation = 540f * (mainProceed - wheelKeyTimes[4])
    }
  }

  // 绘制
  drawAlbumAuthorizationAnimation(
    canvas,
    size,
    photoScale,
    bottomPhotoRotation,
    picShapeOffsetRate,
    wheelScale,
    wheelRotation
  )
}

/**
 * [size] <Float> 图片size，高 = 宽
 * [photoScaleRate] <Float> 两张图片的缩放比例，取值[0-1f]
 * [bottomPhotoRotation] <Float> 底层灰色图片的旋转角度
 * [picShapeOffsetRate] <Float>山和太阳相对于图片的offset比例, offset绝对值 = picShapeOffsetRate * photoSize
 * [wheelScaleRate] <Float> 齿轮旋转动画的角度，取角度值
 * [wheelRotation] <Float> 齿轮旋转动画的角度，取角度值
 */
private fun drawAlbumAuthorizationAnimation(
  canvas: Canvas,
  size: Float,
  photoScaleRate: Float,
  bottomPhotoRotation: Float,
  picShapeOffsetRate: Float,
  wheelScaleRate: Float,
  wheelRotation: Float) {
  // 基本参数
  val photoSize = size * 0.5f

  val wheelIconSize = size * 0.28f
  val wheelMarginX = size * 0.56f
  val wheelMarginY = size * 0.54f

  // 绘制渐变底色的圆形
  val backgroundGradientColors = intArrayOf(HoneyColor.Blue,
    HoneyColor.Green
  )
  val backgroundGradientPositions = floatArrayOf(0f, size)
  drawGradientOval(canvas,
    backgroundGradientColors,
    backgroundGradientPositions,
    size
  )

  canvas.save()
  // 绘制图片
  canvas.translate((size - photoSize) / 2f, (size - photoSize) / 2f)
  drawBottomPhotoSymbol(canvas,
    photoSize, photoScaleRate, bottomPhotoRotation
  )
  drawFrontPhotoSymbol(canvas,
    photoSize, photoScaleRate, picShapeOffsetRate
  )  // 绘制上面带图案的photo
  canvas.restore()

  // 绘制设置icon
  canvas.save()
  drawSettingSymbol(canvas,
    wheelIconSize,
    wheelMarginX,
    wheelMarginY,
    wheelScaleRate,
    wheelRotation
  )
  canvas.restore()
}

private fun drawBottomPhotoSymbol(
  canvas: Canvas,
  size: Float,
  scale: Float,
  rotate: Float) {
  val radius = size * 0.1f
  val paint = EmojiPaint.solidPaint(EmojiUI.lightGray)

  canvas.save()

  canvas.scale(scale, scale, size / 2f, size / 2f)
  canvas.rotate(rotate, size / 2f, size / 2f)
  val rectF = RectF(0f, 0f, size, size)
  canvas.drawRoundRect(rectF, radius, radius, paint)

  canvas.restore()
}

private fun drawFrontPhotoSymbol(
  canvas: Canvas,
  size: Float,
  scale: Float,
  picShapeOffsetRate: Float) {

  // 图片参数
  val radius = size * 0.1f
  val picBackgroundWidth = size * 0.8f
  val picBackgroundHeight = size * 0.66f
  val picMargin = size * 0.1f

  //蓝色图片框Path
  val picBackgroundPath = Path()
  picBackgroundPath.addRect(picMargin, picMargin, picBackgroundWidth + picMargin,
    picBackgroundHeight + picMargin, Path.Direction.CW
  )

  //准备画笔
  val photoPaint = EmojiPaint.solidPaint(Color.WHITE)
  val picBackgroundPaint = EmojiPaint.solidPaint(HoneyColor.Blue)

  //绘制
  canvas.save()
  canvas.scale(scale, scale, size / 2f, size / 2f)

  // 画白色背景
  val rectF = RectF(0f, 0f, size, size)
  canvas.drawRoundRect(rectF, radius, radius, photoPaint)
  // 画蓝色底
  canvas.drawPath(picBackgroundPath, picBackgroundPaint)
  canvas.clipPath(picBackgroundPath)  // 沿着脸的边缘裁切画布
  // 画山和太阳的形状
  drawPicShape(canvas, size,
    size * picShapeOffsetRate
  )
  canvas.restore()
}

// 绘制彩色图片上山和太阳的形状
private fun drawPicShape(
  canvas: Canvas,
  photoSize: Float,
  offsetY: Float) {

  //准备数据
  val ovalSize = photoSize * 0.21f
  val ovalMarginX = photoSize * 0.59f
  val ovalMarginY = photoSize * 0.18f

  //端点
  val AX = photoSize * 0.14f
  val AY = photoSize * 0.7f

  val BX = photoSize * 0.3f
  val BY = photoSize * 0.32f

  val CX = photoSize * 0.5f
  val CY = photoSize * 0.58f

  val DX = photoSize * 0.63f
  val DY = photoSize * 0.51f

  val EX = photoSize * 0.81f
  val EY = photoSize * 0.7f

  //控制点
  val abX = photoSize * 0.22f
  val abY = photoSize * 0.32f

  val bc1X = photoSize * 0.39f
  val bc1Y = photoSize * 0.32f
  val bc2X = photoSize * 0.45f
  val bc2Y = photoSize * 0.58f

  val cd1X = photoSize * 0.55f
  val cd1Y = photoSize * 0.58f
  val cd2X = photoSize * 0.52f
  val cd2Y = photoSize * 0.51f

  val deX = photoSize * 0.76f
  val deY = photoSize * 0.51f

  val path = Path()
  path.moveTo(AX, AY)
  path.quadTo(abX, abY, BX, BY)
  path.cubicTo(bc1X, bc1Y, bc2X, bc2Y, CX, CY)
  path.cubicTo(cd1X, cd1Y, cd2X, cd2Y, DX, DY)
  path.quadTo(deX, deY, EX, EY)
  path.close()
  val rectF = RectF(ovalMarginX, ovalMarginY, ovalSize + ovalMarginX, ovalSize + ovalMarginY)
  path.addOval(rectF, Path.Direction.CW)

  // 画笔
  val paint = EmojiPaint.solidPaint(EmojiUI.lightBlue)

  // 绘制
  canvas.save()
  canvas.translate(0f, offsetY)
  canvas.drawPath(path, paint)

  canvas.restore()

}

fun drawGradientOval(
  canvas: Canvas,
  colors: IntArray,
  positions: FloatArray,
  size: Float) {

  val gradient = LinearGradient(0f, 0f, 0f, size,
    colors,
    positions,
    Shader.TileMode.CLAMP
  )
  val paint = Paint()
  paint.isAntiAlias = true
  paint.shader = gradient
  canvas.drawCircle(size / 2, size / 2, size / 2, paint)

}

fun drawSettingSymbol(
  canvas: Canvas,
  size: Float,
  marginX: Float,
  marginY: Float,
  scale: Float,
  rotate: Float) {
  val innerOvalSize = size * 0.35f

  val path = settingSymbolPath(size)
  val paint = Paint()
  paint.reset()
  paint.isAntiAlias = true
  paint.color = EmojiUI.darkBlue
  paint.style = Paint.Style.FILL

  canvas.save()
  canvas.translate(marginX, marginY)
  canvas.rotate(rotate, size / 2f, size / 2f)
  canvas.scale(scale, scale, size / 2f, size / 2f)

  canvas.drawPath(path, paint)

  paint.color = EmojiUI.lightBlue
  val rectF = RectF((size - innerOvalSize) / 2f, (size - innerOvalSize) / 2f,
    (size + innerOvalSize) / 2f, (size + innerOvalSize) / 2f
  )
  canvas.drawOval(rectF, paint)

  canvas.restore()
}

private fun settingSymbolPath(size: Float): Path {
  val angleCount = 6
  val singleAngle = Math.PI * 2 / angleCount.toDouble()
  val pointRadiusAngle = angleToRadian(14f)
  val innerRadiusAngle = angleToRadian(18f)
  val radius = size / 2
  val innerRadius = size * 0.38f
  val path = Path()
  var angle = -Math.PI / 2
  for (i in 0 until angleCount) {

    val pointAngle1 = -pointRadiusAngle + angle
    val pointAngle2 = pointRadiusAngle + angle
    val innerAngle1 = (singleAngle - innerRadiusAngle) / 2f + angle
    val innerAngle2 = (singleAngle + innerRadiusAngle) / 2f + angle

    val pointX = radius + radius * Math.cos(angle).toFloat()
    val pointY = radius + radius * Math.sin(angle).toFloat()

    val pointRadius1X = radius + radius * Math.cos(pointAngle1).toFloat()
    val pointRadius1Y = radius + radius * Math.sin(pointAngle1).toFloat()

    val pointRadius2X = radius + radius * Math.cos(pointAngle2).toFloat()
    val pointRadius2Y = radius + radius * Math.sin(pointAngle2).toFloat()

    val innerPoint1X = radius + innerRadius * Math.cos(innerAngle1).toFloat()
    val innerPoint1Y = radius + innerRadius * Math.sin(innerAngle1).toFloat()
    val innerPoint2X = radius + innerRadius * Math.cos(innerAngle2).toFloat()
    val innerPoint2Y = radius + innerRadius * Math.sin(innerAngle2).toFloat()

    if (i == 0) {
      path.moveTo(pointRadius1X, pointRadius1Y)
    } else {
      path.lineTo(pointRadius1X, pointRadius1Y)
    }
    path.quadTo(pointX, pointY, pointRadius2X, pointRadius2Y)
    path.lineTo(innerPoint1X, innerPoint1Y)
    path.quadTo(innerPoint1X, innerPoint1Y, innerPoint2X, innerPoint2Y)

    angle += singleAngle
  }
  return path
}
