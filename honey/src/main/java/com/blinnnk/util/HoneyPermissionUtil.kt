package com.blinnnk.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import com.blinnnk.honey.timeUpThen
import com.blinnnk.util.PermissionStatus.hasCameraPermission
import com.blinnnk.util.PermissionStatus.hasGPSFinePermission
import com.blinnnk.util.PermissionStatus.hasGPSPermission
import com.blinnnk.util.PermissionStatus.hasReadStoragePermission
import com.blinnnk.util.PermissionStatus.hasWriteStoragePermission

object PermissionStatus {
  var hasReadStoragePermission = false
  var hasWriteStoragePermission = false
  var hasCameraPermission = false
  var hasGPSPermission = false
  var hasGPSFinePermission = false
}

fun Activity.verifyPermission(permissionCategory: PermissionCategory): Boolean {
  return ActivityCompat.checkSelfPermission(this, permissionCategory.getDetail().category) ==
    PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermissionBy(category: PermissionCategory) {
  ActivityCompat.requestPermissions(this, arrayOf(category.getDetail().category), category.getDetail().requestCode)
}

fun Activity.verifyMultiplePermissions(vararg categories: PermissionCategory) =
  categories.map {
    ActivityCompat
      .checkSelfPermission(this, it.getDetail().category) == PackageManager.PERMISSION_GRANTED }
    .none { !it }


/**
 * 使用协程封装的申请权限及挂机检查权限状态变化, 返回一个含有结果 `Boolean` 的方法.
 * `getAppPermissionStatus` 先从系统全局变量中检查状态, 不会每次都执行方法, 从而提升性能.
 */
fun Activity.checkPermissionListener(vararg categories: PermissionCategory , callback: (Boolean) -> Unit) {
  categories.filterNot { it.checkAppPermissionStatus() }.apply {
    if (isNotEmpty()) {
      coroutinesTask({
        dropWhile { verifyPermission(it) }.map { requestPermissionBy(it) }
      }) {
        // 检查若已经有授权了就更新全局的授权状态
        filter { verifyPermission(it) }.map { it.setAppPermissionStatus() }
        // 这个 `callback` 是 `block` 申请的权限全部都授权了的状态
        callback(all { verifyPermission(it) } )
      }
    } else {
      // 都有授权的话直接返回 `true` 告诉都有授权了
      callback(true)
    }
  }
}

fun Activity.checkPermissionListener(categories: ArrayList<PermissionCategory>, callback: (Boolean) -> Unit) {
  categories.filterNot { it.checkAppPermissionStatus() }.apply {
    if (isNotEmpty()) {
      coroutinesTask({
        dropWhile { verifyPermission(it) }.map { requestPermissionBy(it) }
      }) {
        // 检查若已经有授权了就更新全局的授权状态
        filter { verifyPermission(it) }.map { it.setAppPermissionStatus() }
        // 这个 `callback` 是 `block` 申请的权限全部都授权了的状态, 在弹出授权菜单的时候美 `800ms` 检测一次
        800L timeUpThen {
          callback(all { verifyPermission(it) } )
        }
      }
    } else {
      // 都有授权的话直接返回 `true` 告诉都有授权了
      callback(true)
    }
  }
}

enum class PermissionCategory(val value: Int) {
  Write(0), Read(1), Camera(2), GPSCoarse(3), GPSFine(4);

  data class PermissionDetail(val category: String, val requestCode: Int)

  fun getDetail(): PermissionDetail {
    return when (this.value) {
      Write.value -> PermissionDetail(Manifest.permission.WRITE_EXTERNAL_STORAGE, 102)
      Read.value -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        PermissionDetail(Manifest.permission.READ_EXTERNAL_STORAGE, 102)
      } else {
        TODO("VERSION.SDK_INT < JELLY_BEAN")
      }
      Camera.value -> PermissionDetail(Manifest.permission.CAMERA, 101)
      GPSCoarse.value -> PermissionDetail(Manifest.permission.ACCESS_COARSE_LOCATION, 100)
      GPSFine.value -> PermissionDetail(Manifest.permission.ACCESS_FINE_LOCATION, 100)
      else -> PermissionDetail(Manifest.permission.ACCESS_FINE_LOCATION, 100)
    }
  }
  /**
   * In order not to check the global variable is set, first check the status of the global
   * variable and then decide whether to perform the application of logic to execute.
   */
  fun checkAppPermissionStatus(): Boolean {
    return when(this) {
      Write -> hasWriteStoragePermission
      Read -> hasReadStoragePermission
      Camera -> hasCameraPermission
      GPSCoarse -> hasGPSPermission
      GPSFine -> hasGPSFinePermission
    }
  }

  fun setAppPermissionStatus() {
    return when(this) {
      Write -> hasWriteStoragePermission = true
      Read -> hasReadStoragePermission = true
      Camera -> hasCameraPermission = true
      GPSCoarse -> hasGPSPermission = true
      GPSFine -> hasGPSFinePermission = true
    }
  }
}

/**
 * 检测设备是否含有指定的硬件
 */

enum class HardWareType {
  GPS, Camera
}

fun Activity.hasHardWare(hardWareType: HardWareType): Activity? {
  val hasHardWare = when (hardWareType) {
    HardWareType.Camera -> packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    HardWareType.GPS -> packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
  }
  return if (hasHardWare) this else null
}