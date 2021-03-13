package com.data.local.models

import com.google.gson.annotations.SerializedName


class ResponsePacket<T> {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("num_results")
    var numResults: Int = 0

    //var errors: Array<String> = arrayOf(String())

    @SerializedName("results")
    var results: T? = null


}