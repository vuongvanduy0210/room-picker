package com.gianghv.android.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gianghv.android.views.common.BGType
import es.dmoral.toasty.Toasty

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected var loadingDialog: ProgressDialog? = null

    abstract fun createBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = createBinding()
        setContentView(binding.root)

        setUp()
    }

    open fun setUp() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showLoading(isShow: Boolean) {
        if (isShow) {
            loadingDialog?.dismiss()
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    fun showMessage(context: Context, message: String, bgType: BGType) {
        when (bgType) {
            BGType.BG_TYPE_NORMAL -> Toasty.normal(context, message, 10000).show()
            BGType.BG_TYPE_SUCCESS -> Toasty.success(context, message, 10000, true).show()
            BGType.BG_TYPE_WARNING -> Toasty.warning(context, message, 10000, true).show()
            BGType.BG_TYPE_ERROR -> Toasty.error(context, message, 10000, true).show()
        }
    }
}
