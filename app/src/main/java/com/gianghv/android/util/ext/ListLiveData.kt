package com.gianghv.android.util.ext

import androidx.lifecycle.MutableLiveData

// for mutable list
operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(item)
    this.value = value
}

operator fun <T> MutableLiveData<MutableList<T>>.minusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    if (value.contains(item)) {
        value.remove(item)
    }
    this.value = value
}
