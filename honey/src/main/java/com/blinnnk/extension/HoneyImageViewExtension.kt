package com.blinnnk.extension

import android.graphics.BitmapFactory
import com.blinnnk.uikit.Size
import java.io.File

object ImageUtil {

  fun getImageSize(imagePath: String): Size {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(File(imagePath).absolutePath, options)
    return Size(options.outWidth, options.outHeight)
  }

}