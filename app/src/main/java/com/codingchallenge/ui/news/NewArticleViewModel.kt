package com.codingchallenge.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.BuildConfig
import com.codingchallenge.utils.ErrorResponse
import com.data.enums.ApiEventsEnum
import com.data.enums.SectionEnum
import com.data.local.models.State
import com.data.remote.models.NewsArticle
import com.data.repository.NewsArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewArticleViewModel @Inject constructor(val newsArticleRepository: NewsArticleRepository) :
    ViewModel() {

    var errorResponse: MutableLiveData<ErrorResponse> = MutableLiveData()

    private val _newsUiLiveData = MutableLiveData<State<String>>()

    val newsUiLiveData: LiveData<State<String>>
        get() = _newsUiLiveData


    private val _newsLiveData = MutableLiveData<List<NewsArticle>>()

    val newsLiveData: LiveData<List<NewsArticle>>
        get() = _newsLiveData

    fun setErrorResponse(errorResponse: ErrorResponse) {
        this.errorResponse.value = errorResponse
    }


    fun getResourceString(resourceId: Int): String {
        return try {
            newsArticleRepository.dataManager.getResourceManager().getString(resourceId)
        } catch (e: Exception) {
             ""
        }
    }

    fun getMostViewedNYTimePopularArticle(newsArticlePeriod: Int = 7) {

        viewModelScope.launch(Dispatchers.Default) {
            _newsUiLiveData.postValue(State.loading())
            try {
                val result = newsArticleRepository.getMostViewedNYTimePopularArticle(
                    newsArticlePeriod,
                    SectionEnum.ALL_SECTION.type,
                    BuildConfig.API_KEY
                )

                when (result) {
                    is State.Success -> {
                        newsArticleRepository.apiEventsEnum = ApiEventsEnum.ON_DATA_RECEIVED
                        if ("OK" == result.wrapperData.status) {
                            _newsLiveData.postValue(result.wrapperData.results)
                            _newsUiLiveData.postValue(State.success(""))
                        } else {
                            newsArticleRepository.apiEventsEnum = ApiEventsEnum.ON_NO_DATA_RECEIVED
                            _newsUiLiveData.postValue(State.error<String>("result.wrapperData.results"))
                        }
                    }
                    is State.Error -> {
                        _newsUiLiveData.postValue(State.error<String>(result.serverError))
                    }
                }


            } catch (e: Exception) {
                _newsUiLiveData.postValue(State.error<String>(e.message.toString()))
            }
        }
    }

    fun isLoading(): Boolean {
        return false
    }
}