package com.gianghv.android.views.main

import android.app.DatePickerDialog
import androidx.fragment.app.viewModels
import com.gianghv.android.R
import com.gianghv.android.base.BaseBottomSheetDialog
import com.gianghv.android.databinding.BottomSheetSearchFilterBinding
import com.gianghv.android.util.app.FormatUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class FilterBottomSheetDialog : BaseBottomSheetDialog<BottomSheetSearchFilterBinding>() {

    override val layoutRes = R.layout.bottom_sheet_search_filter

    private val homeViewModel: HomeViewModel by viewModels()

    override fun init() {
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun setUp() {
        binding.apply {
            priceRangeSlider.apply {
                values = listOf(0f, 1000f)
                isTickVisible = false
                addOnChangeListener { slider, _, _ ->
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
                openPickDate {
                    tvCheckInTime.text = it
                }
            }

            layoutCheckOut.setOnClickListener {
                openPickDate {
                    tvCheckOutTime.text = it
                }
            }

            btnDecreasePeople.setOnClickListener {
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
            val m =
                if (monthOfYear + 1 >= 10) (monthOfYear + 1).toString() else "0${monthOfYear + 1}"
            onPicked("$d/$m/$y")
        }, year, month, day)
        dpd.show()
    }

    private fun clear() {
    }
}
