package com.gianghv.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    abstract val layoutRes: Int

    abstract fun init()

    abstract fun setUp()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    fun navigatePopBackstack(direction: NavDirections) {
        findNavController().popBackStack(findNavController().graph.getStartDestination(), false)
        findNavController().navigate(direction)
    }

    protected fun enableUIDrawOnSystemBar() {
        val decorView: View = requireActivity().window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    protected fun setWhiteStatusBar() {
        val decorView: View = requireActivity().window.decorView
        decorView.systemUiVisibility =
            (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                )
    }

    protected fun setLayoutBelowSystemBar(layoutAppBar: View) {
        ViewCompat.setOnApplyWindowInsetsListener(layoutAppBar) { v, insets ->
            val layoutParams = v.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = insets.systemWindowInsetTop
            v.layoutParams = layoutParams
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
