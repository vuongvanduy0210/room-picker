package com.gianghv.android.util.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.gianghv.android.util.network.NetworkUtils

fun ImageView.loadImageCircleWithUrl(url: String) {
    Glide.with(this).load(url).circleCrop().into(this)
}

fun ImageView.loadImageWithUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadImageFitCenter(url: String) {
    var requestOptions: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache strategy

    if (!NetworkUtils.isNetworkAvailable(this.context)) {
        requestOptions = requestOptions.onlyRetrieveFromCache(true)
    }
    Glide.with(this).load(url).apply(requestOptions).fitCenter().into(this)
}

fun ImageView.loadImageCenterCrop(url: String) {
    var requestOptions: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache strategy

    if (!NetworkUtils.isNetworkAvailable(this.context)) {
        requestOptions = requestOptions.onlyRetrieveFromCache(true)
    }
    Glide.with(this).load(url).apply(requestOptions).centerCrop().into(this)
}
