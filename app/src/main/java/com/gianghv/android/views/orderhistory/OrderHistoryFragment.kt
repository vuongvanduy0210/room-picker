package com.gianghv.android.views.orderhistory

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentOrderHistoryBinding
import com.gianghv.android.views.MainActivity
import com.gianghv.android.views.orderhistory.adapter.OrderHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

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
        adapter.setOnOrderClickListener {
            Timber.d("onOrderClick: $it")
            openOrderDetail(it)
        }

        observe()

        val items =
            listOf(OrderHistoryAdapter.FILTER_ALL, OrderHistoryAdapter.FILTER_PAID, OrderHistoryAdapter.FILTER_PENDING)
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinnerFilter.adapter = arrayAdapter

        binding.spinnerFilter.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapter.filter.filter(items[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                adapter.filter.filter("")
            }
        }
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

    private fun openOrderDetail(orderId: String) {
        navigate(OrderHistoryFragmentDirections.actionOrderHistoryFragmentToOrderDetailFragment(id = orderId))
    }
}
