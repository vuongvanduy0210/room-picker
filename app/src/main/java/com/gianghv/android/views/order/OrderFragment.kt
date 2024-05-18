package com.gianghv.android.views.order

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentOrderBinding
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.TypePayment
import com.gianghv.android.util.ext.currentDate
import com.gianghv.android.util.ext.loadImageCenterCrop
import com.gianghv.android.views.DetailActivity
import com.gianghv.android.views.common.BGType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {
    override val layoutRes = R.layout.fragment_order
    private var activity: DetailActivity? = null
    private val viewModel: OrderViewModel by viewModels()
    private var mRoom: Room? = null
    override fun init() {
        activity = requireActivity() as DetailActivity
    }

    override fun setUp() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val id = arguments?.getString("id")
        if (id != null) {
            viewModel.requestRoom(id)
        }

        viewModel.room.observe(viewLifecycleOwner) {
            mRoom = it
            viewModel.calculateTotalPrice()
            updateUI()
        }

        lifecycleScope.launch {
            viewModel.isLoading.collect {
                activity?.showLoading(isShow = it)
            }
        }

        val paymentMethods = arrayOf(TypePayment.CASH, TypePayment.VNPAY)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, paymentMethods)
        binding.spinnerPaymentMethod.adapter = arrayAdapter
        binding.spinnerPaymentMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> viewModel.paymentMethod.value = TypePayment.CASH
                    1 -> viewModel.paymentMethod.value = TypePayment.VNPAY
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.paymentMethod.value = TypePayment.EMPTY
            }
        }

        binding.layoutCheckinDate.setOnClickListener {
            openPickDate { date ->
                viewModel.checkInDate.value = date
            }
        }

        binding.layoutCheckoutDate.setOnClickListener {
            openPickDate { date ->
                viewModel.checkOutDate.value = date
            }
        }

        binding.layoutCheckinTime.setOnClickListener {
            openPickTime { time ->
                viewModel.checkInTime.value = time
            }
        }

        binding.layoutCheckoutTime.setOnClickListener {
            openPickTime { time ->
                viewModel.checkOutTime.value = time
            }
        }

        binding.buttonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.buttonIncreasePeople.setOnClickListener {
            var people = binding.textPeopleCount.text.toString().toInt()
            if (mRoom?.countPeople != null) {
                if (people < mRoom?.countPeople!!) {
                    viewModel.peopleCount.value = ++people
                    viewModel.calculateTotalPrice()
                }
            }
        }

        binding.buttonDecreasePeople.setOnClickListener {
            var people = binding.textPeopleCount.text.toString().toInt()
            if (people > 1) {
                viewModel.peopleCount.value = --people
                viewModel.calculateTotalPrice()
            }
        }

        viewModel.peopleCount.observe(viewLifecycleOwner) {
            viewModel.calculateTotalPrice()
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) {
            activity?.showMessage(requireContext(), it.toString(), BGType.BG_TYPE_WARNING)
        }

        viewModel.orderSuccess.observe(viewLifecycleOwner) {
            if (it) openOrderSuccess()
        }
    }

    private fun updateUI() {
        binding.textBookingDate.currentDate()
        val imageUrl = mRoom?.images?.firstOrNull()?.url.orEmpty()
        binding.imageRoom.loadImageCenterCrop(imageUrl)
    }

    private fun openOrderSuccess() {
        activity?.showMessage(requireContext(), "Đặt phòng thành công", BGType.BG_TYPE_SUCCESS)

        if (viewModel.paymentMethod.value == TypePayment.CASH) {
            Timber.d("Đặt phòng bằng tiền mặt")
            navigate(OrderFragmentDirections.actionOrderFragmentToOrderSuccessFragment())
        } else {
            Timber.d("Đặt phòng bằng VNPay")
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
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }

    private fun openPickTime(onPicked: (String) -> Unit) {
        val currentDateTime = Calendar.getInstance()
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, hour, minute ->
            onPicked("$hour:$minute")
        }, startHour, startMinute, false).show()
    }
}
