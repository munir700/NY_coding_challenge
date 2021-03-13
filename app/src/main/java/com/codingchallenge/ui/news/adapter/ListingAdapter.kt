package com.nyarticles.ui.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.codingchallenge.R
import com.codingchallenge.databinding.RowListingsBinding
import com.codingchallenge.ui.base.adapter.BaseAdapter
import com.codingchallenge.utils.BindingAdapters
import com.data.remote.models.NewsArticle

class ListingAdapter(private val newArticleClickCallback: ((Int, NewsArticle, RowListingsBinding) -> Unit)?) :
    BaseAdapter<NewsArticle, RowListingsBinding>(NewsArticlesDiffCallback) {
    var imageOptions: RequestOptions = RequestOptions()
        .placeholder(R.drawable.img_loading_pics)
        .error(R.drawable.img_loading_pics)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    companion object {
        val NewsArticlesDiffCallback = object : DiffUtil.ItemCallback<NewsArticle>() {
            override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun createBinding(parent: ViewGroup): RowListingsBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_listings,
            parent,
            false
        )
    }

    override fun bind(binding: RowListingsBinding, item: NewsArticle, position: Int) {
        binding.newsArticle = item
        item.media?.takeIf { it.isNotEmpty() }.let {
            it?.get(0)?.mediaMetadata?.get(0)?.url?.let { imgUrl ->
                BindingAdapters.showPhoto(binding.thumbIv, imgUrl)
            }
        }
        binding.root.setOnClickListener {
            binding.newsArticle?.let {
                newArticleClickCallback?.invoke(position, it, binding)
            }
        }
    }
}