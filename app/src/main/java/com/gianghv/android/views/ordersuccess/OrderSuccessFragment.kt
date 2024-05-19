package com.gianghv.android.views.ordersuccess

import androidx.activity.addCallback
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentOrderSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSuccessFragment : BaseFragment<FragmentOrderSuccessBinding>() {
    override val layoutRes = R.layout.fragment_order_success

    override fun init() {
        /* no-op */
    }

    override fun setUp() {
        setLayoutBelowSystemBar(binding.topAppBar)

        binding.buttonBack.setOnClickListener {
            requireActivity().finish()
        }
        binding.buttonHome.setOnClickListener {
            requireActivity().finish()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            /* no-op */
        }
    }
}
