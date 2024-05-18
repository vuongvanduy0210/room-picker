package com.gianghv.android.views.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.fragment.app.viewModels
import com.gianghv.android.R
import com.gianghv.android.base.BaseBottomSheetDialog
import com.gianghv.android.databinding.BottomSheetSearchFilterBinding
import com.gianghv.android.util.app.FormatUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class FilterBottomSheetDialog(val listener: OnApplyFilterClickListener) :
    BaseBottomSheetDialog<BottomSheetSearchFilterBinding>() {

    override val layoutRes = R.layout.bottom_sheet_search_filter

    private val homeViewModel: HomeViewModel by viewModels()

    override fun init() {
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun setUp() {
        binding.apply {
            priceRangeSlider.apply {
                values = listOf(500f, 10000f)
                isTickVisible = false
                addOnChangeListener { slider, _, _ ->
                    val min = slider.values[0].toInt()
                    val max = slider.values[1].toInt()

                    homeViewModel?.setMinPrice(min * 1000)
                    homeViewModel?.setMaxPrice(max * 1000)

                    tvStartPrice.text = FormatUtils.convertEstimatedPriceVND(slider.values[0])
                    tvEndPrice.text = FormatUtils.convertEstimatedPriceVND(slider.values[1])
                }
            }

            btnCancel.setOnClickListener {
                this@FilterBottomSheetDialog.dismiss()
            }

            btnReset.setOnClickListener {
                clear()
            }

            layoutCheckIn.setOnClickListener {
                pickDateTime {
                    tvCheckInTime.text = it
                    homeViewModel?.setCheckInDate(it)
                }
            }

            layoutCheckOut.setOnClickListener {
                pickDateTime {
                    tvCheckOutTime.text = it
                    homeViewModel?.setCheckOutDate(it)
                }
            }

            btnApply.setOnClickListener {
                val searchKeyword = ""
                val checkinDay = homeViewModel?.checkInDate
                val checkoutDay = homeViewModel?.checkOutDate
                val people = homeViewModel?.peopleCount
                val minPrice = homeViewModel?.minPrice
                val maxPrice = homeViewModel?.maxPrice
                listener.onApplyFilter(searchKeyword, checkinDay, checkoutDay, people, minPrice, maxPrice)
                dismiss()
            }
        }
    }

    private fun openPickDate(onPicked: (String) -> Unit) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(requireContext(), { _, y, monthOfYear, dayOfMonth ->
            val d = if (dayOfMonth >= 10) dayOfMonth.toString() else "0$dayOfMonth"
            val m = if (monthOfYear + 1 >= 10) (monthOfYear + 1).toString() else "0${monthOfYear + 1}"
            onPicked("$d/$m/$y")
        }, year, month, day)
        dpd.show()
    }

    private fun pickDateTime(onPicked: (String) -> Unit) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(requireContext(), { _, y, monthOfYear, dayOfMonth ->
            TimePickerDialog(requireContext(), { _, hour, minute ->
                val d = if (dayOfMonth >= 10) dayOfMonth.toString() else "0$dayOfMonth"
                val m = if (monthOfYear + 1 >= 10) (monthOfYear + 1).toString() else "0${monthOfYear + 1}"
                onPicked("$d/$m/$y $hour:$minute")
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

    private fun clear() {
        binding.tvCheckInTime.text = getString(R.string.dd_mm_yy)
        binding.tvCheckOutTime.text = getString(R.string.dd_mm_yy)
        binding.homeViewModel?.clearPeopleCount()
        binding.priceRangeSlider.values = listOf(500f, 10000f)
        homeViewModel.clearFilter()
    }

    interface OnApplyFilterClickListener {
        fun onApplyFilter(
            searchKeyword: String?,
            checkinDay: String?,
            checkoutDay: String?,
            people: Int?,
            minPrice: Int?,
            maxPrice: Int?
        )
    }
}
