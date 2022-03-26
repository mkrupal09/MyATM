package com.example.atmdemo.ui.core

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider


abstract class BaseVMBindingActivity<T : ViewDataBinding, VM : BaseViewModel>(private var viewModelClass: Class<VM>) :
    BaseBindingActivity<T>() {

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[viewModelClass]

        viewModel.loadingLiveData.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel.messageLiveData.observe(this) {
            showMessage(it)
        }
    }
}