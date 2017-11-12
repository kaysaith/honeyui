package com.blinnnk.util

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import com.blinnnk.uikit.GradientStyle
import com.blinnnk.uikit.HoneyColor

object HoneyPaintUtil {

  private val pink = Color.rgb(255, 28, 97)
  private val blue = Color.rgb(28, 183, 255)
  private val green = Color.rgb(15, 210, 88)

  fun honeyLinearGradient(height: Float, gradientStyle: GradientStyle): LinearGradient = when (gradientStyle) {
    GradientStyle.PinkToYellow -> LinearGradient(0f, 0f, 0f, height, pink , Color.YELLOW, Shader.TileMode.CLAMP)
    GradientStyle.BlueToGreen -> LinearGradient(0f, 0f, 0f, height, blue , green , Shader.TileMode.CLAMP)
    GradientStyle.BlackToRed -> LinearGradient(0f, 0f, 0f, height, Color.BLACK, HoneyColor.Red, Shader.TileMode.CLAMP)
    GradientStyle.RedToPink -> LinearGradient(0f, 0f, 0f, height, Color.RED, HoneyColor.Yellow, Shader.TileMode.CLAMP)
  }

}