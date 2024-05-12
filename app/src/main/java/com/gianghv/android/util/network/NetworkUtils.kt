package com.gianghv.android.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object NetworkUtils {
    /**
     * Check that device network is available
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val check = activeNetworkInfo != null && activeNetworkInfo.isConnected
        Log.d(TAG, "Network check: $check")
        return check
    }

    private const val TAG = "NetUtils"
}
