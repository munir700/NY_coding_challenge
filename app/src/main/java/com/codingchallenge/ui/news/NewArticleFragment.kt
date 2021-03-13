package com.codingchallenge.ui.news

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingchallenge.R
import com.codingchallenge.databinding.FragmentNewsArticleBinding
import com.codingchallenge.enums.ErrorResponseEnum
import com.codingchallenge.ui.base.BaseFragment
import com.codingchallenge.ui.newsdetail.NewsArticleDetailsActivity
import com.codingchallenge.utils.ErrorResponse
import com.data.enums.ApiEventsEnum
import com.data.local.models.State
import com.data.remote.models.NewsArticle
import com.nyarticles.ui.news.adapter.ListingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewArticleFragment :
    BaseFragment<NewArticleViewModel, FragmentNewsArticleBinding>(R.layout.fragment_news_article) {

    private val newsArticleListAdapter: ListingAdapter by lazy {
        ListingAdapter { position, newArticle, binding ->
            NewsArticleDetailsActivity.openActivityForResult(
                requireActivity(),
                newArticle,
                binding.thumbIv,
                0,
                position
            )
        }
    }

    override val viewModel: NewArticleViewModel by viewModels()

    override fun getBindingVariable(): Int {
        return 0//BR.vm
    }

    override fun onInitDataBinding() {
        updateProgress()
        viewModel.getMostViewedNYTimePopularArticle();
        bindings.recyclerResults.adapter = newsArticleListAdapter
        bindings.recyclerResults.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == newsArticleListAdapter.itemCount - 1) {
                    bindings.recyclerResults.post {
                        /*viewModel.isPerformingNextQuery = true
                        viewModel.searchNextPage()*/
                    }
                }
            }
        })
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            val copy: List<NewsArticle> = ArrayList(it)
            newsArticleListAdapter.submitList(copy)
        })

        bindings.searchAgainBtn.setOnClickListener { view ->
            val newPeriod = bindings.periodEt.text?.trim().toString()
            if (newPeriod.isNotEmpty()) {
                viewModel.getMostViewedNYTimePopularArticle(newPeriod.toInt())
            } else {
                viewModel.getMostViewedNYTimePopularArticle()
            }
        }

        bindings.pullToRefresh.setOnRefreshListener {
            bindings.pullToRefresh.isRefreshing = false
            viewModel.getMostViewedNYTimePopularArticle();
        }

        bindings.button.setOnClickListener {
            val newPeriod = bindings.periodEt.text?.trim().toString()
            if (newPeriod.isNotEmpty()) {
                viewModel.getMostViewedNYTimePopularArticle(newPeriod.toInt())
            }
        }
    }


    private fun updateProgress() {
        viewModel.newsUiLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is State.Success -> {
                    hideProgress()
                    bindings.pullToRefresh.visibility = View.VISIBLE
                    bindings.constraintError.visibility = View.GONE
                }
                is State.Error -> {
                    hideProgress()
                    when (viewModel.newsArticleRepository.apiEventsEnum.eventType) {
                        ApiEventsEnum.ON_NO_DATA_RECEIVED.eventType -> {
                            if (it.serverError.isNotEmpty()) {
                                onApiRequestFailed(it.serverError)
                            }

                            bindings.pullToRefresh.visibility = View.GONE
                            bindings.constraintError.visibility = View.VISIBLE
                            newsArticleListAdapter.submitList(listOf())
                            viewModel.setErrorResponse(
                                ErrorResponse.Builder(ErrorResponseEnum.NO_DATA_RECEIVED).build()
                            )
                        }

                        ApiEventsEnum.NO_INTERNET_CONNECTION.eventType -> {
                            bindings.pullToRefresh.visibility = View.GONE
                            bindings.constraintError.visibility = View.VISIBLE
                            viewModel.setErrorResponse(
                                ErrorResponse.Builder(ErrorResponseEnum.NO_INTERNET_CONNECTION)
                                    .build()
                            )
                        }

                        ApiEventsEnum.ON_API_REQUEST_FAILURE.eventType -> {
                            bindings.constraintError.visibility = View.VISIBLE
                            viewModel.setErrorResponse(
                                ErrorResponse.Builder(ErrorResponseEnum.API_REQUEST_FAILURE).build()
                            )
                        }
                    }

                }
                is State.Loading -> {
                    showProgress()
                }
            }
        })
    }

    companion object {
        const val LISTING_POSITION = "listing_index"
        const val NEWARTICLE_INFO_INTENT_KEY = "new_article"
    }
}

