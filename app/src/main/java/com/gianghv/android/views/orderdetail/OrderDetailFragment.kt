package com.gianghv.android.views.orderdetail

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentOrderDetailBinding
import com.gianghv.android.domain.OrderStatus
import com.gianghv.android.domain.TypePayment
import com.gianghv.android.util.app.PermissionHelper
import com.gianghv.android.util.ext.loadImageCenterCrop
import com.gianghv.android.util.ext.parseDateDMYHM
import com.gianghv.android.util.file.FileUtils
import com.gianghv.android.views.OrderDetailActivity
import com.gianghv.android.views.common.BGType
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>() {
    override val layoutRes = R.layout.fragment_order_detail
    private var activity: OrderDetailActivity? = null
    private val viewModel: OrderDetailViewModel by viewModels()
    private val selectedImages = mutableListOf<Uri>()
    private val imageUrls = mutableListOf<String>()

    override fun init() {
        activity = requireActivity() as OrderDetailActivity

        PermissionHelper.requestImageUploadPermission(requireContext(), this)
    }

    private fun chooseFileFromStorage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            viewModel.imageUrls.value?.clear()

            if (data.clipData != null) {
                val count: Int = data.clipData?.itemCount ?: 0
                for (i in 0 until count) {
                    if (selectedImages.size < MAX_IMAGES_ALLOWED) {
                        val imageUri: Uri? = data.clipData?.getItemAt(i)?.uri

                        val uri: Uri? = data.clipData?.getItemAt(i)?.getUri()

                        if (uri != null) selectedImages.add(uri)
                    } else {

                    }
                }
            } else if (data.data != null) {
                val uri: Uri? = data.data

                // do something with the uri
                if (uri != null) selectedImages.add(uri)
            }
        }
        if (selectedImages.isNotEmpty()) handleImageSelected();
    }

    private fun handleImageSelected() {
        val filePaths = mutableListOf<String>()
        imageUrls.clear()
        // Handle the selected images here
        activity?.showLoading(true)

        for (uri in selectedImages) {
            // Do something with the selected image
            println("Selected image: " + uri.path)
            val path: String? = FileUtils.getPathFromUri(requireContext(), uri)
            val filePath = path ?: ""

            println("File path: $filePath")
            filePaths.add(filePath)
        }

        try {
            val storage = Firebase.storage
            val storageRef = storage.reference

            // Create a child reference
            // imagesRef now points to "images"
            val imagesRef: StorageReference = storageRef.child("images")

            for (uri in selectedImages) {
                val index = selectedImages.indexOf(uri)
                var uploaded = 0
                // Do something with the selected image
                println("Selected image: " + uri.path)

                val spaceRef = imagesRef.child(uri.lastPathSegment ?: "image${System.currentTimeMillis()}")

                val uploadTask = spaceRef.putFile(uri)
                uploadTask.addOnSuccessListener {
                    spaceRef.downloadUrl.addOnSuccessListener {
                        uploaded++
                        Timber.d("Image URL: $it")
                        imageUrls.add(it.toString())
                        if (uploaded == selectedImages.size)
                            activity?.showLoading(false)
                    }.addOnFailureListener {
                        uploaded++
                        it.printStackTrace()
                        if (uploaded == selectedImages.size)
                            activity?.showLoading(false)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setUp() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val id = activity?.intent?.extras?.getString("id")
        if (id != null) {
            viewModel.getOrderDetail(id)
        } else activity?.onBackPressed()

        lifecycleScope.launch {
            viewModel.isLoading.collect {
                activity?.showLoading(isShow = it)
            }
        }

        binding.buttonBack.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.buttonUploadImage.setOnClickListener {
            chooseFileFromStorage()
        }

        binding.buttonEvaluate.setOnClickListener {
            viewModel.evaluate(binding.ratingbar.rating.toDouble(), binding.edtContent.text.toString(), imageUrls)
        }

        viewModel.isEvaluate.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it)
                    activity?.showMessage(requireContext(), "Đánh giá thành công", BGType.BG_TYPE_SUCCESS)
                else
                    activity?.showMessage(requireContext(), "Đánh giá thất bại", BGType.BG_TYPE_ERROR)
            }

        }

        observe()
    }

    private fun observe() {
        viewModel.room.observe(viewLifecycleOwner) {
            if (it != null) {
                updateUI()
            }
        }
    }

    private fun updateUI() {
        binding.textBookingDate.text = viewModel.orderDetail?.bookingDate?.parseDateDMYHM()
        binding.textCheckinTime.text = viewModel.orderDetail?.startDate?.parseDateDMYHM()
        binding.textCheckoutTime.text = viewModel.orderDetail?.endDate?.parseDateDMYHM()

        val imageUrl = viewModel.room.value?.images?.firstOrNull()?.url.toString()
        binding.imageRoom.loadImageCenterCrop(imageUrl)

        when (viewModel.orderDetail?.typePayment) {
            TypePayment.EMPTY -> binding.textPaymentMethod.text = "Chưa xác định"
            TypePayment.CASH -> binding.textPaymentMethod.text = "Tiền mặt"
            TypePayment.VNPAY -> binding.textPaymentMethod.text = "VNPay"
            null -> binding.textPaymentMethod.text = "Chưa xác định"
        }

        when (viewModel.orderDetail?.status) {
            OrderStatus.PENDING -> {
                binding.textOrderStatus.text = "Chờ thanh toán"
            }

            OrderStatus.PAYED -> {
                binding.textOrderStatus.text = "Đã thanh toán"
            }

            OrderStatus.COMPLETED -> {
                binding.textOrderStatus.text = "Đã xác nhận"
            }

            OrderStatus.DEPOSIT -> {
                binding.textOrderStatus.text = "Đã hủy"
            }

            null -> binding.textOrderStatus.text = "Chưa xác định"
        }

        when (viewModel.orderDetail?.status) {
            OrderStatus.PENDING -> {
                if (viewModel.orderDetail?.typePayment == TypePayment.VNPAY) {
                    binding.groupEvaluate.visibility = View.GONE
                    binding.containerPay.visibility = View.VISIBLE
                } else {
                    binding.groupEvaluate.visibility = View.GONE
                    binding.containerPay.visibility = View.GONE
                }
            }

            OrderStatus.PAYED -> {
                binding.groupEvaluate.visibility = View.GONE
                binding.containerPay.visibility = View.GONE
            }

            OrderStatus.COMPLETED -> {
                binding.groupEvaluate.visibility = View.VISIBLE
                binding.containerPay.visibility = View.GONE
            }

            OrderStatus.DEPOSIT, null -> {
                binding.groupEvaluate.visibility = View.GONE
                binding.containerPay.visibility = View.GONE
            }
        }
    }

    companion object {
        const val MAX_IMAGES_ALLOWED = 1

    }

}
