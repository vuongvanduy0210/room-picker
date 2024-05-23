package com.gianghv.android.util.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionHelper {
    fun requestImageUploadPermission(context: Context, fragment: Fragment): Boolean {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permission = arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
            fragment.requestPermissions(permission, REQUEST_IMAGE_UPLOAD_PERMISSION)
        } else {
            return true
        }
        return false
    }

    const val REQUEST_IMAGE_UPLOAD_PERMISSION = 111
}
