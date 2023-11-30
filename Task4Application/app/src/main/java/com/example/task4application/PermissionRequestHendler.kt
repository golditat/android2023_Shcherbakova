package com.example.task4application

import android.content.Intent
import android.media.audiofx.BassBoost
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.task4application.controller.MyActivity

class PermissionRequestHandler(
    private val activity: AppCompatActivity,
    private val callback: (() -> Unit)? = null,
    private val rationaleCallback: (() -> Unit)? = null,
    private val deniedCallback: (() -> Unit)? = null,
) {

    private var currentPermission = ""
    private var permissionDeniedCount = 0

    private val singlePermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            handlePermissionResult(isGranted)
        }

    private val multiplePermissionsLauncher: ActivityResultLauncher<Array<String>> =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
            val isAllGranted = permissionsMap.all { it.value }
            handlePermissionResult(isAllGranted)
        }

    fun requestPermission(permission: String) {
        this.currentPermission = permission
        if (shouldShowRationale()) {
            showRationaleDialog()
        } else {
            launchPermissionRequest()
        }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        if (isGranted) {
            callback?.invoke()
        } else {
            if (shouldShowRationale()) {
                rationaleCallback?.invoke()
            } else {
                permissionDeniedCount++
                if (permissionDeniedCount > 2) {
                    showSettingsDialog()
                } else {
                    deniedCallback?.invoke()
                }
            }
        }
    }

    private fun shouldShowRationale(): Boolean {
        return currentPermission.isNotEmpty() && activity.shouldShowRequestPermissionRationale(currentPermission)
    }

    private fun launchPermissionRequest() {
        singlePermissionLauncher.launch(currentPermission)
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("This app requires the following permission to function properly.")
            .setPositiveButton("OK") { _, _ ->
                launchPermissionRequest()
            }
            .setNegativeButton("Cancel") { _, _ ->
                deniedCallback?.invoke()
            }
            .show()
    }


    private fun showSettingsDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Permission Required")
            .setMessage("This app requires the following permission. Please go to app settings to enable it.")
            .setPositiveButton("Settings") { _, _ ->
                openAppSettings()
            }
            .setNegativeButton("Cancel") { _, _ ->
                deniedCallback?.invoke()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }}