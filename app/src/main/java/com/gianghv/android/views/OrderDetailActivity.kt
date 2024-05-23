package com.gianghv.android.views

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.gianghv.android.R
import com.gianghv.android.base.BaseActivity
import com.gianghv.android.base.ProgressDialog
import com.gianghv.android.databinding.ActivityOrderDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding>() {
    override fun createBinding(): ActivityOrderDetailBinding {
        return ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        super.setUp()

        val id = intent.getStringExtra("id")
        Timber.d("id: $id")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_order_detail) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.order_detail_nav)
        navGraph.setStartDestination(R.id.orderDetailFragment)
        navController.setGraph(
            navGraph,
            Bundle().apply {
                putString("id", id)
            }
        )

        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
    }
}
