package com.gianghv.android.views.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gianghv.android.databinding.ItemEvaluationBinding
import com.gianghv.android.domain.RoomEvaluation

class EvaluationAdapter : RecyclerView.Adapter<EvaluationAdapter.ViewHolder>() {

    private val evaluationList: MutableList<RoomEvaluation> = mutableListOf()

    class ViewHolder(val binding: ItemEvaluationBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateList(list: List<RoomEvaluation>) {
        evaluationList.clear()
        evaluationList.addAll(list)
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
        val binding = holder.binding as ItemEvaluationBinding
        binding.textName.text = item.userId
        binding.textRating.text = item.star.toString()
        binding.textContent.text = item.content

        val imageAdapter = ImageEvaluationAdapter(item.images)
        val layoutManager = GridLayoutManager(holder.itemView.context, 3)

        binding.recyclerEvaluationImage.layoutManager = layoutManager
        binding.recyclerEvaluationImage.adapter = imageAdapter
    }
}
