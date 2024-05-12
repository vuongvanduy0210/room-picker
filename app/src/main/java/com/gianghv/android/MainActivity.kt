package com.gianghv.android

import com.gianghv.android.base.BaseActivity
import com.gianghv.android.base.ProgressDialog
import com.gianghv.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setUp() {
        super.setUp()
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
    }
}
