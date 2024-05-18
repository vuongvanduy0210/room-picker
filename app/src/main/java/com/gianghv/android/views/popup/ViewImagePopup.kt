package com.gianghv.android.views.popup

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.gianghv.android.R
import com.gianghv.android.util.ext.loadImageFitCenter
import timber.log.Timber

class ViewImagePopup : DialogFragment() {

    private lateinit var dialog: Dialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val imageUrl = arguments?.getString(ARG_IMAGE_URL)

        dialog = Dialog(requireContext(), R.style.Theme_Main_Popup)

        with(dialog) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.popup_fullscreen_image_view)
            show()
        }

        dialog.setContentView(R.layout.popup_fullscreen_image_view)
        val imageView = dialog.findViewById<ImageView>(R.id.fullscreen_image)

        Timber.d("Show image: $imageUrl")
        imageView.loadImageFitCenter(imageUrl.toString())

        imageView.setOnClickListener { _ -> dialog.dismiss() }

        dialog.findViewById<FrameLayout>(R.id.container).setOnClickListener {
            dialog.dismiss()
        }

        imageView.setOnClickListener { _ -> dialog.dismiss() }
        return dialog
    }

    companion object {
        private const val ARG_IMAGE_URL = "image_url"

        fun newInstance(imageUrl: String): ViewImagePopup {
            val fragment = ViewImagePopup()
            val args = Bundle().apply {
                putString(ARG_IMAGE_URL, imageUrl)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
