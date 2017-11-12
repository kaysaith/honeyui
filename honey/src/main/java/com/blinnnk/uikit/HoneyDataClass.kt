package com.blinnnk.uikit

import android.util.Property
import android.view.View

data class ValueAnimationOfIntParameters(
  val type: AttributeAnimationType,
  val startValue: Int,
  val endValue: Int
)

data class ObjectAnimatorValue(
  val type: Property<View, Float>,
  val finalValue: Float
)

data class Size(val width: Int, val height: Int)