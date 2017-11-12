package com.blinnnk.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.blinnnk.honey.R

@SuppressLint("PrivateResource")
inline fun <reified T : Activity> Activity.jump() {
  startActivity(Intent(this, T::class.java))
  overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom,
    R.anim.abc_shrink_fade_out_from_bottom
  )
  finish()
}

@SuppressLint("PrivateResource")
inline fun <reified T : Fragment> Activity.addFragment(containerID: Int, fragmentTag: String? = null) {
  val willAppearFragment = T::class.java.newInstance()
  (this as? AppCompatActivity)
    ?.supportFragmentManager
    ?.beginTransaction()
    ?.setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_popup_exit)
    ?.add(containerID, willAppearFragment, fragmentTag)
    ?.commit()
}

inline fun <reified T : Fragment> Activity.replaceFragment(containerID: Int, fragmentTag: String? = null) {
  val willAppearFragment = T::class.java.newInstance()
  (this as? AppCompatActivity)
    ?.supportFragmentManager
    ?.beginTransaction()
    ?.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
    ?.replace(containerID, willAppearFragment, fragmentTag)
    ?.commit()
}

@SuppressLint("PrivateResource")
inline fun <reified T : Fragment> Activity.addFragmentAndSetArguments(containerID: Int, fragmentTag: String? = null, setArgument: Bundle.() -> Unit) {
  val willAppearFragment = T::class.java.newInstance()
  val bundle = Bundle()
  setArgument(bundle)
  willAppearFragment.arguments = bundle
  (this as? AppCompatActivity)
    ?.supportFragmentManager
    ?.beginTransaction()
    ?.setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_popup_exit)
    ?.add(containerID, willAppearFragment, fragmentTag)
    ?.commit()
}

inline fun <reified T : Fragment> Activity.replaceFragmentAndSetArguments(containerID: Int, block: Bundle.() -> Unit) {
  val willAppearFragment = T::class.java.newInstance()
  val arguments = Bundle()
  block(arguments)
  willAppearFragment.arguments = arguments
  (this as? AppCompatActivity)
    ?.supportFragmentManager
    ?.beginTransaction()
    ?.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
    ?.replace(containerID, willAppearFragment)
    ?.commit()
}

fun Activity.removeFragment(fragment: Fragment) {
  (this as? AppCompatActivity)
    ?.supportFragmentManager
    ?.beginTransaction()
    ?.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
    ?.remove(fragment)?.commit()
}