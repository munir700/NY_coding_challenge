package com.core.utils

import java.io.IOException
import java.io.InputStream

object StringUtils {

    fun inputStreamToString(inputStream: InputStream): String {
        try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            return String(bytes)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return ""
    }


}
