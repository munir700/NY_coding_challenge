package com.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.data.enums.SectionEnum
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsNull.notNullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_getNewsArticle() = runBlocking {
        enqueueResponse("news_article.json")
        val newArticles = apiService.getMostViewedNYTimePopularArticle(
            section = SectionEnum.ALL_SECTION.type,
            period = 7,
            apiKey = "q71nyctb41ZYdqpkeE3QaUX6XoXNbzqu",
        ).body()

        assertThat(newArticles, notNullValue())
        assertThat(newArticles?.results?.get(0)?.id, `is`(100000007615891))
        assertThat(newArticles?.results?.get(0)?.assetId, `is`(100000007615891))
        assertThat(newArticles?.results?.get(0)?.section, `is`("U.S."))
        assertThat(newArticles?.results?.get(0)?.type, `is`("Article"))

    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
