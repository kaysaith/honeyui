package com.blinnnk.font

import android.content.Context
import android.graphics.Typeface

/**
 * @date 12/11/2017 2:55 PM
 * @author KaySaith
 */

object HoneyFont {

  val honey: Context.() -> Typeface =
    fun Context.(): Typeface = Typeface.createFromAsset(assets, "fonts/honeyFont.ttf")

}