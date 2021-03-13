package com.data.repository

import com.data.base.BaseRepository
import com.data.enums.SectionEnum
import com.data.local.models.ResponsePacket
import com.data.local.models.State
import com.data.manager.DataManager
import com.data.remote.models.NewsArticle
import javax.inject.Inject

class NewsArticleRepository @Inject constructor(override val dataManager: DataManager) :
    BaseRepository(dataManager) {


    suspend fun getMostViewedNYTimePopularArticle(
        newsArticlePeriod: Int,
        section: String = SectionEnum.ALL_SECTION.type,
        apiKey: String
    ): State<ResponsePacket<List<NewsArticle>>> {
        return makeApiCall {
            dataManager.getApiHelper()
                .getMostViewedNYTimePopularArticle(section, newsArticlePeriod, apiKey)
        }
    }
}