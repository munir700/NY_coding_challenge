package com.codingchallenge.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

/**
 * Abstract Activity which binds [ViewModel] [VM] and [ViewDataBinding] [VB]
 */
abstract class BaseActivity<VM : ViewModel, VB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : AppCompatActivity() {

    protected abstract val viewModel: VM

    protected lateinit var bindings: VB

    protected lateinit var context: Context

    protected abstract fun getBindingVariable(): Int

    companion object {
        fun <T> newIntent(context: Context, activityName: Class<T>): Intent {
            return Intent(context, activityName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        bindings = DataBindingUtil.setContentView(this, layoutResId)
       // bindings.setVariable(getBindingVariable(), viewModel)
        bindings.lifecycleOwner = this
    }
}
