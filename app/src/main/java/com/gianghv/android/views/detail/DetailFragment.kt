package com.gianghv.android.views.detail

import android.view.View
import androidx.fragment.app.viewModels
import com.gianghv.android.R
import com.gianghv.android.base.BaseFragment
import com.gianghv.android.databinding.FragmentDetailBinding
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomType
import com.gianghv.android.views.DetailActivity
import com.gianghv.android.views.detail.adapter.EvaluationAdapter
import com.gianghv.android.views.detail.adapter.ImageViewAdapter
import com.gianghv.android.views.popup.ViewImagePopup
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override val layoutRes = R.layout.fragment_detail
    private var activity: DetailActivity? = null
    private lateinit var mRoomId: String
    private val imageViewAdapter: ImageViewAdapter by lazy { ImageViewAdapter() }
    private val evaluationAdapter: EvaluationAdapter by lazy { EvaluationAdapter() }
    private val viewModel: DetailViewModel by viewModels()
    private var mRoom: Room? = null

    override fun init() {
        activity = requireActivity() as DetailActivity
    }

    override fun setUp() {
        binding.lifecycleOwner = viewLifecycleOwner
        val args: DetailFragmentArgs? = activity?.intent?.extras?.let { DetailFragmentArgs.fromBundle(it) }
        mRoomId = args?.id.toString()

        binding.viewpagerImage.adapter = imageViewAdapter
        binding.recyclerEvaluation.adapter = evaluationAdapter

        binding.buttonBack.setOnClickListener { activity?.onBackPressed() }

        viewModel.requestRoom(mRoomId)

        viewModel.roomLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Timber.d("Room: $it")
                imageViewAdapter.updateImages(it.images)
                mRoom = it
                updateUI()
            }
        }

        viewModel.users.observe(viewLifecycleOwner) {
            evaluationAdapter.updateUserList(it)
        }

        imageViewAdapter.setOnImageItemClickListener {
            showImage(it)
        }

        binding.buttonOrder.setOnClickListener {
            openOrderScreen()
        }
    }

    private fun updateUI() {
        binding.textPrice.text = "${mRoom?.price}VND"
        binding.textName.text = mRoom?.name
        binding.textDesc.text = mRoom?.desc
        binding.textNumberOfPeople.text = mRoom?.countPeople.toString()

        if (mRoom?.evaluation.isNullOrEmpty()) {
            binding.textEvaluation.visibility = View.GONE
        } else {
            binding.textEvaluation.visibility = View.VISIBLE
            binding.textEvaluation.text = mRoom?.getEvaluationAverage().toString()
        }

        if (mRoom?.type == RoomType.Vip) {
            binding.textRoomTypeVip.visibility = View.VISIBLE
        } else {
            binding.textRoomTypeVip.visibility = View.GONE
        }

        mRoom?.evaluation?.let { evaluationAdapter.updateList(it) }
    }

    private fun showImage(image: String) {
        val fragmentManager = requireActivity().supportFragmentManager
        ViewImagePopup.newInstance(image).show(fragmentManager, ViewImagePopup::class.simpleName)
    }

    private fun openOrderScreen() {
        navigate(DetailFragmentDirections.actionDetailFragmentToOrderFragment(mRoomId))
    }
}
