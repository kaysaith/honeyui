package com.blinnnk.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration
import android.view.WindowManager
import com.blinnnk.honey.R

@SuppressLint("PrivateResource")
inline fun <reified T : Activity> Activity.jump() {
    startActivity(Intent(this, T::class.java))
    overridePendingTransition(
            R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom
    )
    finish()
}

@SuppressLint("PrivateResource")
inline fun <reified T : Fragment> Activity.addFragment(
        containerID: Int,
        fragmentTag: String? = null
) {
    val willAppearFragment = T::class.java.newInstance()
    (this as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.add(containerID, willAppearFragment, fragmentTag)?.commitAllowingStateLoss()
}

@SuppressLint("PrivateResource")
inline fun <reified T : Fragment> Activity.replaceFragment(
        containerID: Int,
        fragmentTag: String? = null
) {
    val willAppearFragment = T::class.java.newInstance()
    (this as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(containerID, willAppearFragment, fragmentTag)?.commitAllowingStateLoss()
}

@SuppressLint("PrivateResource")
inline fun <reified T : Fragment> Activity.addFragmentAndSetArguments(
        containerID: Int,
        fragmentTag: String? = null,
        setArgument: Bundle.() -> Unit = {}
) {
    val willAppearFragment = T::class.java.newInstance()
    val bundle = Bundle()
    setArgument(bundle)
    willAppearFragment.arguments = bundle
    (this as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.add(containerID, willAppearFragment, fragmentTag)?.commitAllowingStateLoss()
}

inline fun <reified T : Fragment> Activity.replaceFragmentAndSetArguments(
        containerID: Int,
        block: Bundle.() -> Unit = {}
) {
    val willAppearFragment = T::class.java.newInstance()
    val arguments = Bundle()
    block(arguments)
    willAppearFragment.arguments = arguments
    (this as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(containerID, willAppearFragment)?.commitAllowingStateLoss()
}

fun Activity.removeFragment(fragment: Fragment) {
    (this as? AppCompatActivity)?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(fragment)?.commit()
}

// Hide Status Bar
fun Activity.hideStatusBar() {
    window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

inline fun <reified T : AppCompatActivity> Fragment.reboot() {
    (activity as? T)?.apply {
        finish()
        startActivity(intent)
    }
}

inline fun <reified T : Activity> Context.rebootApp() {
    val mStartActivity = Intent(this, T::class.java)
    val mPendingIntentId = 123456
    val mPendingIntent = PendingIntent.getActivity(
            this,
            mPendingIntentId,
            mStartActivity,
            PendingIntent.FLAG_CANCEL_CURRENT
    )
    val mgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
    System.exit(0)
}

fun Activity.navigationBarIsHidden(): Boolean {
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
    return !hasMenuKey && !hasBackKey
}

fun Context.navigationBarIsHidden(): Boolean {
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
    return !hasMenuKey && !hasBackKey
}

