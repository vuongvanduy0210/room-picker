package com.gianghv.android.views.sign_in

import androidx.navigation.fragment.findNavController
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutRes: Int = R.layout.fragment_login

    override fun setUp() {
        binding.btnGoToSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
    }
}
