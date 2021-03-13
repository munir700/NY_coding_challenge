package com.data.remote

import com.data.local.models.ResponsePacket
import com.data.remote.models.NewsArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Service to fetch Data using end point [API_URL].
 */
interface ApiService {

    // mostviewed/all-sections/{period}.json
    @GET("mostviewed/{section}/{period}.json")
    suspend fun getMostViewedNYTimePopularArticle(
        @Path("section") section: String, @Path("period") period: Int, @Query("api-key") apiKey: String
    ): Response<ResponsePacket<List<NewsArticle>>>

}