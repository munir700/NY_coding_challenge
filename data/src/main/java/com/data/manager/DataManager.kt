package com.data.manager

import com.data.remote.ApiService
import com.data.utils.ResourceProvider


interface DataManager {

    fun getApiHelper(): ApiService

    fun getResourceManager(): ResourceProvider

}