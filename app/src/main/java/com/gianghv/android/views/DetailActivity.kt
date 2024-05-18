package com.gianghv.android.views

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.gianghv.android.R
import com.gianghv.android.base.BaseActivity
import com.gianghv.android.base.ProgressDialog
import com.gianghv.android.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override fun createBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        super.setUp()

        val id = intent.getStringExtra("id")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(
            navController.graph,
            Bundle().apply {
                putString("id", id)
            }
        )

        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
    }
}
