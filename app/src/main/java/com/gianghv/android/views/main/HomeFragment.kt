package com.gianghv.android.views.main

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentHomeBinding
import com.gianghv.android.domain.Room
import com.gianghv.android.views.MainActivity
import com.gianghv.android.views.main.adapter.HotelAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), FilterBottomSheetDialog.OnApplyFilterClickListener {

    override val layoutRes = R.layout.fragment_home

    private var activity: MainActivity? = null

    private val adapter: HotelAdapter by lazy { HotelAdapter() }

    private val viewModel: HomeViewModel by viewModels()

    private var searchKeyword: String? = null
    private var checkinDay: String? = null
    private var checkoutDay: String? = null
    private var people: Int? = null
    private var minPrice: Int? = null
    private var maxPrice: Int? = null

    override fun init() {
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {
        binding.rcvListRoom.layoutManager = LinearLayoutManager(activity)
        binding.rcvListRoom.adapter = adapter
        binding.homeViewModel = viewModel

        setLayoutBelowSystemBar(binding.topAppBar)

        viewModel.roomList.observe(viewLifecycleOwner) {
            Timber.d("LOG DATA: $it")
            adapter.updateItems(it)
        }

        viewModel.orderList.observe(viewLifecycleOwner) {
            adapter.updateOrderList(it)
        }

        binding.btFilter.setOnClickListener {
            val filterDialog = FilterBottomSheetDialog(this)
            filterDialog.show(childFragmentManager, filterDialog.tag)
        }

        viewModel.requestRooms()

        adapter.setOnItemClick { room: Room -> openDetail(room) }

        binding.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchKeyword = v.text.toString()
                applyFilter()
                true
            }
            false
        }
    }

    private fun openDetail(room: Room) {
        navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id = room.id))
    }

    private fun applyFilter() {
        Timber.d("LOG DATA: $searchKeyword, $checkinDay, $checkoutDay, $people, $minPrice, $maxPrice")

        val constraintStr =
            HotelAdapter.filterBuilder(searchKeyword, checkinDay, checkoutDay, people, minPrice, maxPrice)
        adapter.filter.filter(constraintStr)
    }

    override fun onApplyFilter(
        searchKeyword: String?,
        checkinDay: String?,
        checkoutDay: String?,
        people: Int?,
        minPrice: Int?,
        maxPrice: Int?
    ) {
        this.searchKeyword = searchKeyword
        this.checkinDay = checkinDay
        this.checkoutDay = checkoutDay
        this.people = people
        this.minPrice = minPrice
        this.maxPrice = maxPrice
        applyFilter()
    }
}
