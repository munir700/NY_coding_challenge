package com.codingchallenge.ui.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<out T : ViewDataBinding> constructor(val bindings: T) :
    RecyclerView.ViewHolder(bindings.root)