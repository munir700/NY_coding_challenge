package com.core.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(
    AndroidJUnit4::class
)
class NetworkUtilsTest {
    @Test
    fun testIsConnectedToInternet() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        // Run the test
        val result: Boolean = NetworkUtils.isNetworkConnected(appContext)

        // Verify the results
        assertTrue(result)
    }

}