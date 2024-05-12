package com.gianghv.android

import com.gianghv.android.base.BaseActivity
import com.gianghv.android.databinding.ActivitySignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override fun createBinding() = ActivitySignInBinding.inflate(layoutInflater)
}
