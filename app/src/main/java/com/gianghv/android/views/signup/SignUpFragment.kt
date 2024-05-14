package com.gianghv.android.views.signup

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gianghv.android.MainActivity
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentSignUpBinding
import com.gianghv.android.domain.BGType
import com.gianghv.android.util.app.AppUtils
import com.gianghv.android.views.common.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    override val layoutRes: Int = R.layout.fragment_sign_up

    private var activity: MainActivity? = null

    private val authViewModel: AuthViewModel by viewModels()

    override fun init() {
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {
        binding.apply {
            tvTitle.text = SpannableString("Đăng Ký Tài Khoản").apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_dark)),
                    0,
                    4,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_dark)),
                    8,
                    11,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            layoutLogin.setOnClickListener {
                findNavController().popBackStack()
            }

            btnSignUp.setOnClickListener {
                onClickSignUp()
            }
        }

        lifecycleScope.launch {
            authViewModel.responseMessage.collect {
                activity?.showMessage(requireContext(), it.message, it.bgType)
            }
        }
        lifecycleScope.launch {
            authViewModel.isLoading.collect {
                if (it) {
                    activity?.showLoading()
                } else {
                    activity?.hideLoading()
                }
            }
        }
    }

    private fun onClickSignUp() {
        activity?.hideKeyboard()
        val email = binding.edtEmail.text?.trim().toString()
        val name = binding.edtName.text?.trim().toString()
        val pass = binding.edtPassword.text?.trim().toString()
        val confirm = binding.edtConfirmPassword.text?.trim().toString()
        if (!AppUtils.isValidatedName(name)) {
            activity?.showMessage(requireContext(), "Tên không hợp lệ!", BGType.BG_TYPE_ERROR)
            binding.edtName.setText("")
            return
        }
        if (!AppUtils.isValidatedEmail(email)) {
            activity?.showMessage(requireContext(), "Email không hợp lệ!", BGType.BG_TYPE_ERROR)
            binding.edtEmail.setText("")
            return
        }
        if (!AppUtils.isValidatedPassword(pass)) {
            activity?.showMessage(requireContext(), "Mật khẩu không hợp lệ!", BGType.BG_TYPE_ERROR)
            binding.edtPassword.setText("")
            return
        }
        if (!AppUtils.isValidatedConfirmPass(pass, confirm)) {
            activity?.showMessage(requireContext(), "Xác nhận mật khẩu không hợp lệ!", BGType.BG_TYPE_ERROR)
            binding.edtConfirmPassword.setText("")
            return
        }
        authViewModel.signUp(
            email = email,
            name = name,
            password = pass
        )
    }
}
