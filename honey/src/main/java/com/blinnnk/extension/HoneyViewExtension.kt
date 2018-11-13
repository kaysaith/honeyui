@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package com.blinnnk.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.blinnnk.uikit.ScreenSize
import com.blinnnk.uikit.matchParentViewGroup
import com.blinnnk.uikit.uiPX

fun View.getDisplayHeight(): Int {
    val rect = Rect()
    (context as Activity).window.decorView.getWindowVisibleDisplayFrame(rect)
    return rect.bottom - rect.top
}

fun Context?.safeToast(message: CharSequence) {
    if (this != null) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    } else {
        return
    }
}

fun Fragment?.safeToast(message: CharSequence) {
    if (this != null) {
        context.safeToast(message)
    } else {
        return
    }
}

fun View.preventDuplicateClicks() {
    isClickable = false
    500L timeUpThen { isClickable = true } // 2秒后才可以再次点击
}

fun View.addCorner(
        radius: Int,
        backgroundColor: Int,
        clipOut: Boolean = true
) {
    val shape = GradientDrawable().apply {
        cornerRadius = radius.uiPX() / 2f
        shape = GradientDrawable.RECTANGLE
        setSize(matchParentViewGroup, matchParentViewGroup)
        setColor(backgroundColor)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (clipOut) {
            clipToOutline = true
        }
    }
    setBackgroundDrawable(shape)
}

fun View.addTopLRCorner(
        radius: Float,
        backgroundColor: Int
) {
    val shape = GradientDrawable().apply {
        cornerRadii = floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f)
        shape = GradientDrawable.RECTANGLE
        setSize(matchParentViewGroup, matchParentViewGroup)
        setColor(backgroundColor)
    }
    setBackgroundDrawable(shape)
}

/**
 * @description
 * 下面都是关于 `Shape` 的便捷方法, 主要场景有设定 `Corner`, 设定 `Border`
 */

fun View.addCircleBorder(
        radius: Int,
        borderWidth: Int,
        borderColor: Int = Color.TRANSPARENT
) {
    val shape = GradientDrawable().apply {
        cornerRadius = radius.toInt().toFloat()
        shape = GradientDrawable.RECTANGLE
        setSize(matchParentViewGroup, matchParentViewGroup)
        setColor(Color.TRANSPARENT)
        setStroke(borderWidth, borderColor)
    }
    setBackgroundDrawable(shape)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        clipToOutline = true
    }
}

fun View.addBottomCorner(
        radius: Float,
        backgroundColor: Int
) {
    val shape = GradientDrawable().apply {
        cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, radius, radius, radius, radius)
        shape = GradientDrawable.RECTANGLE
        setSize(matchParentViewGroup, matchParentViewGroup)
        setColor(backgroundColor)
    }
    setBackgroundDrawable(shape)
}

/**
 * This function is used to simulate the click event with the program, the application
 * scenario is a local unit test, or other need to use the scene.
 */
@SuppressLint("Recycle")
fun View.imitateClick() {
    1000L timeUpThen {
        var downTime = SystemClock.uptimeMillis()

        val downEvent = MotionEvent.obtain(
                downTime, downTime, MotionEvent.ACTION_DOWN, 10f, 10f, 0
        )
        downTime += 1000

        val upEvent = MotionEvent.obtain(
                downTime, downTime, MotionEvent.ACTION_UP, 10f, 10f, 0
        )

        onTouchEvent(downEvent)
        onTouchEvent(upEvent)
        downEvent.recycle()
        upEvent.recycle()
    }
}

/**
 * @description 便捷的给 `View` 设定在 `Relative Layout` 父级中相对位置
 * 当父级不是 `Anko Layout` 的时候无法便捷实用 `LParams` 的时候可以使用这个方法布局
 */

fun <T : View> T.setCenterInHorizontal() {
    layoutParams.let {
        (it as? RelativeLayout.LayoutParams)?.apply { addRule(RelativeLayout.CENTER_HORIZONTAL) }
    }
}

fun <T : View> T.setCenterInVertical() {
    layoutParams.let {
        (it as? RelativeLayout.LayoutParams)?.apply { addRule(RelativeLayout.CENTER_VERTICAL) }
    }
}

fun <T : View> T.setCenterInParent() {
    layoutParams.let {
        (it as? RelativeLayout.LayoutParams)?.apply { addRule(RelativeLayout.CENTER_IN_PARENT) }
    }
}

fun <T : View> T.setAlignParentRight() {
    layoutParams.let {
        (it as? RelativeLayout.LayoutParams)?.apply { addRule(RelativeLayout.ALIGN_PARENT_RIGHT) }
    }
}

fun <T : View> T.setAlignParentBottom() {
    layoutParams.let {
        (it as? RelativeLayout.LayoutParams)?.apply { addRule(RelativeLayout.ALIGN_PARENT_BOTTOM) }
    }
}

inline fun <T : ViewGroup.MarginLayoutParams> View.setMargins(block: T.() -> Unit) {
    (layoutParams as? T).let {
        if (it != null) {
            block(it)
        } else {
            return
        }
    }
}

fun <T : View> T.into(parent: ViewGroup) {
    parent.addView(this)
}

fun <T : View> T.isHidden(): T {
    this.visibility = View.GONE
    return this
}

/**
 * Mobile phones with virtual operating columns such as `SamSung S8, S9`
 * can't get the real screen height through conventional methods
 * Here, two methods are added to get the real screen height
 */

fun Context.getRealScreenHeight(): Int {
    val displaySize = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        (this as? Activity)?.windowManager?.defaultDisplay?.getRealSize(displaySize)
    }
    return displaySize.y
}

fun Context.getScreenHeightWithoutStatusBar(): Int {
    val displaySize = Point()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        (this as? Activity)?.windowManager?.defaultDisplay?.getRealSize(displaySize)
    }
    return displaySize.y - ScreenSize.statusBarHeight
}

fun View.keyboardHeightListener(block: (keyboardHeight: Int) -> Unit) {
    val fullHeight = Resources.getSystem().displayMetrics.heightPixels
    addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        if (getDisplayHeight() != fullHeight) {
            block(fullHeight - getDisplayHeight())
        } else {
            block(0)
        }
    }
}

fun View.updateHeightByText(
        content: String,
        textSize: Float,
        maxWidth: Int,
        maxHeight: Int,
        lineHeight: Int = 20.uiPX()
) {
    val textWidth = Resources.getSystem().displayMetrics.density * content.measureTextWidth(textSize)
    // 测算文字的内容高度来修改 `Cell` 的高度布局
    Math.floor(textWidth / maxWidth.toDouble()).let {
        val finalHeight = (it * lineHeight).toInt()
        layoutParams.height += if (finalHeight > maxHeight) maxHeight else finalHeight
    }
}

fun View.resetViewHeightByText(
        defaultHeight: Int,
        content: String,
        textSize: Float,
        maxWidth: Int,
        maxHeight: Int,
        lineHeight: Int = 20.uiPX()
) {
    val textWidth = Resources.getSystem().displayMetrics.density * content.measureTextWidth(textSize)
    // 测算文字的内容高度来修改 `Cell` 的高度布局
    Math.floor(textWidth / maxWidth.toDouble()).let {
        val finalHeight = (it * lineHeight).toInt()
        layoutParams.height = defaultHeight + if (finalHeight > maxHeight) maxHeight else finalHeight
    }
}

fun View.getViewAbsolutelyPositionInScreen(): IntArray {
    val cords = intArrayOf(0, 0)
    getLocationOnScreen(cords)
    return cords
}
