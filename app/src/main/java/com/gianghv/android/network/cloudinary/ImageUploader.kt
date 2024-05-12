package com.gianghv.android.network.cloudinary

import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

object ImageUploader {
    fun upload(
        uri: Uri,
        onStart: ((String) -> Unit)?,
        onProgress: ((Long, Long) -> Unit)?,
        onSuccess: ((Map<*, *>) -> Unit)?,
        onError: ((ErrorInfo) -> Unit)?,
        onReschedule: ((ErrorInfo) -> Unit)?
    ) {
        MediaManager.get().upload(uri).callback(object : UploadCallback {
            override fun onStart(requestId: String) {
                onStart(requestId)
            }

            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                onProgress?.invoke(bytes, totalBytes)
            }

            override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                onSuccess?.invoke(resultData)
            }

            override fun onError(requestId: String, error: ErrorInfo) {
                onError?.invoke(error)
            }

            override fun onReschedule(requestId: String, error: ErrorInfo) {
                onReschedule?.invoke(error)
            }
        }).dispatch()
    }
}
