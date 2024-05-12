package com.gianghv.android.base

import android.app.Dialog
import android.content.Context
import com.gianghv.android.R

class ProgressDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.progress_dialog)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}
