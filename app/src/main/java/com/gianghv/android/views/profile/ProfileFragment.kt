package com.gianghv.android.views.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentProfileBinding
import com.gianghv.android.views.AuthActivity
import com.gianghv.android.views.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutRes = R.layout.fragment_profile
    private var activity: MainActivity? = null
    private val viewModel: ProfileViewModel by viewModels()
    override fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {

        observe()

        binding.buttonLogout.setOnClickListener {
            viewModel.logout()

            val intent = Intent(requireActivity(), AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                activity?.showLoading(isShow = it)
            }
        }

        viewModel.order.observe(viewLifecycleOwner) { order ->
            if (!order.isNullOrEmpty()) {
                var totalRentCount = 0
                var totalCost = 0
                order.forEach {
                    totalRentCount++
                    totalCost += it.price
                }

                binding.textCost.text = "$totalCost VND"
                binding.textRentCount.text = "$totalRentCount"
            }
        }
    }
}
