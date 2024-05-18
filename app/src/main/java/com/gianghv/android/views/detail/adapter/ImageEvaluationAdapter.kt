package com.gianghv.android.views.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gianghv.android.databinding.ItemImageCardBinding
import com.gianghv.android.domain.Image
import com.gianghv.android.util.ext.loadImageCenterCrop

class ImageEvaluationAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<ImageEvaluationAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemImageCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.binding.image.loadImageCenterCrop(image.url)
    }
}
