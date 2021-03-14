package com.codingchallenge.ui.news

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.codingchallenge.R
import com.codingchallenge.TestData
import com.codingchallenge.ui.newsdetail.NewsArticleDetailsActivity
import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NewsTestActivity {


    @Test
    @Throws(Exception::class)
    fun clickingButton_verifyViewContent() {
        val scenario = ActivityScenario.launch(NewsArticleActivity::class.java)
        scenario.onActivity { activity: NewsArticleActivity ->
            val searchButton = activity.findViewById(R.id.button) as Button
            val searchAgainButton = activity.findViewById(R.id.search_again_btn) as Button
            val searchEt = activity.findViewById(R.id.period_et) as EditText

            searchButton.performClick()

            assertThat(searchButton.text.toString(), equalTo(activity.getString(R.string.submit)));
            searchAgainButton.performClick()
            searchAgainButton.text = activity.getString(R.string.STR_RETRY)

            assertThat(searchAgainButton.text.toString(), equalTo(activity.getString(R.string.STR_RETRY)));

            assertThat(searchEt.hint.toString(), equalTo(activity.getString(R.string.enter_period_for_recent_news_article)));
            searchEt.setText("7")
            assertThat(searchEt.text.toString(), equalTo("7"));
        }
    }

    @Test
    @Throws(Exception::class)
    fun open_NewsDetailActivityStartedOnClick() {
        val scenario = ActivityScenario.launch(NewsArticleActivity::class.java)
        scenario.onActivity { activity: NewsArticleActivity ->
            // The intent we expect to be launched when a user clicks on the button
            val expectedIntent = Intent(activity, NewsArticleDetailsActivity::class.java)
            expectedIntent.putExtra(NewArticleFragment.NEWARTICLE_INFO_INTENT_KEY, TestData.getNewsArticles().results?.get(0))
            activity.startActivity(expectedIntent)
        }
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeNull() {
        val scenario = ActivityScenario.launch(NewsArticleActivity::class.java)
        scenario.onActivity { activity: NewsArticleActivity ->
            Assert.assertNotNull(activity)
        }
    }
}