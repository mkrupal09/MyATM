package com.example.atmdemo.ui.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()

    fun invalidateLoading(show: Boolean) {
        loadingLiveData.postValue(show)
    }

    fun showMessage(message: String) {
        messageLiveData.postValue(message)
    }

}