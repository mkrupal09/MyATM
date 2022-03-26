package com.example.atmdemo.ui.core

import android.app.Dialog
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


open class BaseActivity : AppCompatActivity() {

    var dialog: Dialog? = null
    val TAG = "ActivityLifeCycle:" + this::class.java.simpleName

    open fun showLoading() {
    }


    open fun hideLoading() {
        Log.e("Loading", "On Hidden")
        dialog?.dismiss()
    }


    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun makeStatusAware() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}
