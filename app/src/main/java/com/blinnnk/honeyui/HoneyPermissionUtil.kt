package com.blinnnk.honeyui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

/**
 * @date 11/11/2017 9:03 PM
 * @author KaySaith
 */

var hasWriteStoragePermission = false
var hasReadStoragePermission = false
var hasCameraPermission = false
var hasGPSPermission = false
var hasGPSFinePermission = false

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
 * Using coroutine encapsulation, application permissions and on-hook check permission status
 * changes, returning a method containing the result `Boolean`.
 * `getAppPermissionStatus` checks the status of system global variables first and does not
 * execute methods each time to improve performance.
 */
fun Activity.checkPermissionListener(vararg categories: PermissionCategory , callback: (Boolean) -> Unit) {
  categories.filterNot { it.checkAppPermissionStatus() }.apply {
    if (isNotEmpty()) {
      coroutinesTask({
        dropWhile { verifyPermission(it) }.map { requestPermissionBy(it) }
      }) {
        // Check if there is already authorized to update the global authorization status
        filter { verifyPermission(it) }.map { it.setAppPermissionStatus() }
        // This `callback` is the status of all the permissions that the` block` application has been granted
        callback(all { verifyPermission(it) } )
      }
    } else {
      // Are authorized directly return `true` Tell have authorized
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
        // Check if there is already authorized to update the global authorization status
        filter { verifyPermission(it) }.map { it.setAppPermissionStatus() }
        // The `callback` is the state of the application for which` block` is all authorized,
        // and the `800ms` is checked once when the authorization menu pops up
        800L timeUpThen {
          callback(all { verifyPermission(it) } )
        }
      }
    } else {
      // Are authorized directly return `true` Tell have authorized
      callback(true)
    }
  }
}

enum class PermissionCategory(private val value: Int) {
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