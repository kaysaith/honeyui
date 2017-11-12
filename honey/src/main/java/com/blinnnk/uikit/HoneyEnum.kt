package com.blinnnk.uikit

enum class AttributeAnimationType {
  Width, Height, Padding, PaddingTop, PaddingBottom, MarginLeft, MarginBottom
}

enum class RippleMode {
  Square, Round
}

enum class FloatAnimationObject {
  Alpha, ScaleX, ScaleY, X, Y
}


enum class GradientStyle {
  PinkToYellow, BlueToGreen, BlackToRed, RedToPink
}

data class Position(
  val left: Float,
  val top: Float
)

enum class ShineText {
  TopTitle, BottomTittle, Progress
}