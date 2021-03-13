package com.codingchallenge.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.codingchallenge.R
import com.codingchallenge.utils.dialog.ActionDialog

abstract class BaseFragment<VM : ViewModel, VB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    protected abstract val viewModel: VM

    protected lateinit var bindings: VB

    protected var progressBar: LinearLayout? = null

    protected var baseActivity: BaseActivity<*, *>? = null

    protected abstract fun getBindingVariable(): Int

    abstract fun onInitDataBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity<*, *>)
            baseActivity = context
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindings = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        bindings.lifecycleOwner = this
        bindings.setVariable(getBindingVariable(), viewModel)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitDataBinding()


        //TODO use layout in xml.It is recommended for approact.
        //otherwise the binding object is going to be null and non of the views are accessable.
        progressBar = bindings.root.findViewById(R.id.progressBar)
        progressBar?.visibility = View.GONE


    }

    open fun hideProgress() {
        if (progressBar?.isShown == true) {
            progressBar?.visibility = View.GONE
        }
    }

    open fun showProgress() {
        if (progressBar?.isShown == false) {
            progressBar?.visibility = View.VISIBLE
        }
    }

    /**
     * on Server Request Failed.
     */
    open fun onApiRequestFailed(message: String) {
        ActionDialog(requireContext(), message, null, true).show()
    }
}