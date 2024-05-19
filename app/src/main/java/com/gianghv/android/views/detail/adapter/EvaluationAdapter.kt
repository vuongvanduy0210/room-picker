package com.gianghv.android.views.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gianghv.android.databinding.ItemEvaluationBinding
import com.gianghv.android.domain.RoomEvaluation
import com.gianghv.android.domain.User
import timber.log.Timber

class EvaluationAdapter : RecyclerView.Adapter<EvaluationAdapter.ViewHolder>() {

    private val evaluationList: MutableList<RoomEvaluation> = mutableListOf()
    private val userList: MutableList<User> = mutableListOf()

    class ViewHolder(val binding: ItemEvaluationBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateList(list: List<RoomEvaluation>) {
        evaluationList.clear()
        evaluationList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateUserList(list: List<User>) {
        userList.clear()
        userList.addAll(list)
        Timber.d("userList: $userList")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEvaluationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return evaluationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = evaluationList[position]
        val binding = holder.binding
        Timber.d("item: $item")

        val user = userList.find { it.id == item.userId }
        binding.textName.text = user?.name

        binding.textRating.text = item.star.toString()
        binding.textContent.text = item.content

        val imageAdapter = ImageEvaluationAdapter(item.images)
        val layoutManager = GridLayoutManager(holder.itemView.context, 2)

        binding.recyclerEvaluationImage.layoutManager = layoutManager
        binding.recyclerEvaluationImage.adapter = imageAdapter
    }
}
