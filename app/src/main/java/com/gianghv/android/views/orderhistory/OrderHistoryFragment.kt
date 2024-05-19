package com.gianghv.android.views.orderhistory

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentOrderHistoryBinding
import com.gianghv.android.views.MainActivity
import com.gianghv.android.views.orderhistory.adapter.OrderHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderHistoryFragment : BaseFragment<FragmentOrderHistoryBinding>() {
    override val layoutRes: Int = R.layout.fragment_order_history
    private val viewModel: OrderHistoryViewModel by viewModels()
    private var activity: MainActivity? = null
    private val adapter: OrderHistoryAdapter by lazy { OrderHistoryAdapter() }

    override fun init() {
        activity = requireActivity() as MainActivity
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun setUp() {
        setLayoutBelowSystemBar(binding.topAppBar)
        binding.recycler.adapter = adapter

        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.isLoading.collect {
                activity?.showLoading(isShow = it)
            }
        }

        viewModel.orders.observe(viewLifecycleOwner) {
            viewModel.rooms.value?.let { it1 -> adapter.setItems(it, it1) }
        }
    }
}
