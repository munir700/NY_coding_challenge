package com.data.local

import androidx.room.TypeConverter
import com.data.remote.models.NewsArticle
import com.google.gson.Gson

class TypeConverter {

    @TypeConverter
    fun listToJson(value: List<NewsArticle>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun stringToList(value: String): List<NewsArticle> {
        return Gson().fromJson(value, Array<NewsArticle>::class.java).toList()
    }
}