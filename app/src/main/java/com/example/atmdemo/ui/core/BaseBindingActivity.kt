package com.example.atmdemo.ui.core

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<T : ViewDataBinding> : BaseActivity() {

    lateinit var binding: T

    fun setBindingView(layoutId: Int) {
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

}