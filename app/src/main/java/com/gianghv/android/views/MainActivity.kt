package com.gianghv.android.views

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.gianghv.android.R
import com.gianghv.android.base.BaseActivity
import com.gianghv.android.base.ProgressDialog
import com.gianghv.android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setUp() {
        super.setUp()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
    }
}
