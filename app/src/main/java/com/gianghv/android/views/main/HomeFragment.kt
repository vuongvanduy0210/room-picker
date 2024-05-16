package com.gianghv.android.views.main

import com.gianghv.android.MainActivity
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutRes = R.layout.fragment_home

    private var activity: MainActivity? = null

    override fun init() {
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {
        binding.btFilter.setOnClickListener {
            val filterDialog = FilterBottomSheetDialog()
            filterDialog.show(childFragmentManager, filterDialog.tag)
        }
    }
}
