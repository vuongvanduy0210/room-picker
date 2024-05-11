package com.gianghv.android.util.ext

import android.app.Activity
import android.view.View

/**
 * Help layout can draw over system status bar
 */
fun Activity.enableUIDrawOnSystemBar() {
    val decorView: View = window.decorView
    decorView.systemUiVisibility =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

fun Activity.setWhiteStatusBar() {
    val decorView: View = window.decorView
    decorView.systemUiVisibility =
        (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            )
}
