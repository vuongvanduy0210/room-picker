package com.gianghv.android.views

import com.gianghv.android.base.BaseActivity
import com.gianghv.android.base.ProgressDialog
import com.gianghv.android.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {
    override fun createBinding(): ActivityAuthBinding {
        return ActivityAuthBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        super.setUp()
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
    }
}
