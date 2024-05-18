package com.gianghv.android.views.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gianghv.android.databinding.ItemImageViewBinding
import com.gianghv.android.domain.Image
import com.gianghv.android.util.ext.loadImageFitCenter

class ImageViewAdapter : RecyclerView.Adapter<ImageViewAdapter.ViewHolder>() {
    private val images: MutableList<Image> = mutableListOf()
    private var onImageItemClickListener: ((String) -> Unit)? = null

    fun setOnImageItemClickListener(listener: (String) -> Unit) {
        onImageItemClickListener = listener
    }

    fun updateImages(newImages: List<Image>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemImageViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        val imageUrl = image.url
        holder.binding.imageView.loadImageFitCenter(imageUrl)
        holder.binding.root.setOnClickListener {
            onImageItemClickListener?.invoke(image.url)
        }
    }
}
