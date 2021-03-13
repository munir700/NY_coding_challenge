package com.data.manager

import com.data.remote.ApiService
import com.data.utils.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerImpl @Inject constructor(
    private val apiService: ApiService,
    private val resourceProvider: ResourceProvider,
) : DataManager {

    override fun getApiHelper(): ApiService {
        return apiService
    }

    override fun getResourceManager(): ResourceProvider {
        return resourceProvider
    }
}