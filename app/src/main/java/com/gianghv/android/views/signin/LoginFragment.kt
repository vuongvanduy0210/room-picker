package com.gianghv.android.views.signin

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
import com.gianghv.android.databinding.FragmentLoginBinding
import com.gianghv.android.domain.BGType
import com.gianghv.android.util.app.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutRes: Int = R.layout.fragment_login

    private var activity: MainActivity? = null

    private val signInViewModel: SignInViewModel by viewModels()

    override fun init() {
        activity = requireActivity() as MainActivity
    }

    override fun setUp() {
        binding.apply {
            tvTitle.text = SpannableString("Welcome Back").apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_dark)),
                    8,
                    12,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            btnLogin.setOnClickListener {
                onClickLogin()
            }

            layoutForgotPassword.setOnClickListener {
            }

            layoutSignUp.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                )
            }
        }

        lifecycleScope.launch {
            signInViewModel.responseMessage.collect {
                activity?.showMessage(requireContext(), it.message, it.bgType)
            }
        }
        lifecycleScope.launch {
            signInViewModel.isLoading.collect {
                if (it) {
                    activity?.showLoading()
                } else {
                    activity?.hideLoading()
                }
            }
        }
    }

    private fun onClickLogin() {
        activity?.hideKeyboard()
        val email = binding.edtEmail.text?.trim().toString()
        val pass = binding.edtPassword.text?.trim().toString()
        if (!AppUtils.isValidatedEmail(email)) {
            activity?.showMessage(requireContext(), "Email không đúng!", BGType.BG_TYPE_ERROR)
            binding.edtEmail.setText("")
            return
        }
        if (!AppUtils.isValidatedPassword(pass)) {
            activity?.showMessage(requireContext(), "Mật khẩu không đúng!", BGType.BG_TYPE_ERROR)
            binding.edtPassword.setText("")
            return
        }
        signInViewModel.login(email, pass)
    }
}
