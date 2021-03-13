package com.data.base

import com.core.utils.NetworkUtils
import com.data.R
import com.data.enums.ApiEventsEnum
import com.data.local.models.State
import com.data.manager.DataManager
import retrofit2.Response

open class BaseRepository(open val dataManager: DataManager) {

    var apiEventsEnum = ApiEventsEnum.ON_API_CALL_START

    protected open fun isNetworkNotAvailable(): Boolean {
        var isConnected = true
        if (!NetworkUtils.isNetworkConnected(dataManager.getResourceManager().getContext())) {
            isConnected = false
        }
        return !isConnected
    }

    suspend fun <T : Any> makeApiCall(
        call: suspend () -> Response<T>,
    ): State<T> {
        if (isNetworkNotAvailable()) {
            apiEventsEnum = ApiEventsEnum.NO_INTERNET_CONNECTION
            return State.Error(
                dataManager.getResourceManager().getString(
                    R.string.internet_error
                )
            )
        }

        return apiOutput(call)

    }


    private suspend fun <T : Any> apiOutput(
        call: suspend () -> Response<T>,
    ): State<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            State.Success(response.body()!!)
        else {
            apiEventsEnum = ApiEventsEnum.ON_API_REQUEST_FAILURE
            State.Error(
                response.message()
            )
        }
    }
}