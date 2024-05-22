package com.gianghv.android.views.common

import android.view.View
import android.widget.ArrayAdapter
import com.gianghv.android.R
import com.gianghv.android.base.BaseBottomSheetDialog
import com.gianghv.android.databinding.BottomSheetEditUserBinding
import com.gianghv.android.domain.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditUserBottomSheetDialog(val user: User, val allowChangeRole: Boolean, val listener: OnApplyEditUserListener) :
    BaseBottomSheetDialog<BottomSheetEditUserBinding>() {

    interface OnApplyEditUserListener {
        fun onApplyEditUser(user: User)
    }

    override val layoutRes = R.layout.bottom_sheet_edit_user
    private val roleItems = listOf("admin", "member")

    override fun init() {
    }

    override fun setUp() {
        val roleAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, roleItems)
        binding.spinnerRole.adapter = roleAdapter
        initUI()

        binding.btnApply.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val role = binding.spinnerRole.selectedItem.toString()

            val newUser = user.copy(name = name, email = email, role = role)
            listener.onApplyEditUser(newUser)
            dismiss()
        }

        if (!allowChangeRole) {
            binding.tvRole.visibility = View.GONE
            binding.spinnerRole.isEnabled = false
            binding.spinnerRole.visibility = View.GONE
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnReset.setOnClickListener {
            initUI()
        }

    }

    private fun initUI() {
        binding.edtName.setText(user.name)
        binding.edtEmail.setText(user.email)
        binding.spinnerRole.setSelection(roleItems.indexOf(user.role))
    }

}
