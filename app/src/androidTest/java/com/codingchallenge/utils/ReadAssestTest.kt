package com.codingchallenge.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream

@RunWith(AndroidJUnit4::class)
class ReadAssetTest {

    @Test
    fun test_ToReadAssetsFileInAndroidTestContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertNotNull(appContext)
        val input: InputStream = appContext.assets.open("news_article.json")
        Assert.assertNotNull(input)
    }
}