package com.gianghv.android.views.main

import com.gianghv.android.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : BaseViewModel() {

    private val _peopleNumber = MutableStateFlow(1)
    val peopleNumber: StateFlow<Int> get() = _peopleNumber

    fun increasePeopleNumber() {
        _peopleNumber.value++
    }

    fun decreasePeopleNumber() {
        if (peopleNumber.value == 1) return
        _peopleNumber.value--
    }
}
