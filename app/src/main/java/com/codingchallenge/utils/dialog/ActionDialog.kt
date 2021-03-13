package com.codingchallenge.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.AlphaAnimation
import androidx.databinding.DataBindingUtil
import com.codingchallenge.R
import com.codingchallenge.databinding.ActionDialogBinding
import com.codingchallenge.interfaces.ActionDialogCallBack

class ActionDialog(
    context: Context,
    private val message: String,
    private val dialogCallBack: ActionDialogCallBack?,
    private val singleAction: Boolean
) : Dialog(context), View.OnClickListener {
    lateinit var binding: ActionDialogBinding

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.action_dialog,
            null,
            false
        )
        this.setContentView(binding.root)
        binding.tvMessage.text = message

        binding.btnOk.visibility = if (singleAction) View.VISIBLE else View.GONE
        binding.groupYesNoBtns.visibility = if (singleAction) View.GONE else View.VISIBLE

        if (singleAction) {
            binding.btnOk.setOnClickListener(this)
        } else {
            binding.btnYes.setOnClickListener(this)
            binding.btnNo.setOnClickListener(this)
        }

        window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }


    override fun onClick(v: View?) {
        val alphaAnimator = AlphaAnimation(1f, 0.8f)
        v?.let {
            when (it.id) {
                R.id.btn_yes -> {
                    binding.btnYes.startAnimation(alphaAnimator)
                    dialogCallBack?.onDialogPositiveButton()
                }
                R.id.btn_no -> {
                    binding.btnNo.startAnimation(alphaAnimator)
                    dialogCallBack?.onDialogNegativeButton()
                }
                R.id.btn_ok -> {
                    dialogCallBack?.onDialogNeutralButton()
                }
            }
            dismiss()
        }
    }
}