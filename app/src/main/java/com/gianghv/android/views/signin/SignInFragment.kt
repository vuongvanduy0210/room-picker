package com.gianghv.android.views.signin

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentSignInBinding
import com.gianghv.android.util.app.AppUtils
import com.gianghv.android.views.AuthActivity
import com.gianghv.android.views.common.AuthViewModel
import com.gianghv.android.views.common.BGType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    override val layoutRes: Int = R.layout.fragment_sign_in

    private var activity: AuthActivity? = null

    private val authViewModel: AuthViewModel by viewModels()

    override fun init() {
        activity = requireActivity() as AuthActivity
    }

    override fun setUp() {
        authViewModel.getAccessToken()
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
                navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }
        }

        lifecycleScope.launch {
            authViewModel.responseMessage.collect {
                activity?.showMessage(requireContext(), it.message, it.bgType)
            }
        }
        lifecycleScope.launch {
            authViewModel.isLoading.collect {
                activity?.showLoading(isShow = it)
            }
        }
        lifecycleScope.launch {
            authViewModel.isSignedIn.collect {
                if (it) {
                    // Clear the entire back stack
                    navigatePopBackstack(SignInFragmentDirections.actionSignInFragmentToMainNav())
                }
            }
        }
    }

    private fun onClickLogin() {
        activity?.hideKeyboard()
        val email = binding.edtEmail.text?.trim().toString()
        val pass = binding.edtPassword.text?.trim().toString()
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
        authViewModel.signIn(email, pass)
    }
}
