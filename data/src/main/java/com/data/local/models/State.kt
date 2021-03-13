package com.data.local.models


/**
 * State Management for UI & Data.
 */
sealed class State<out T> {

    class Loading<T>() : State<T>()

    data class Success<T>(val wrapperData: T) : State<T>()

    data class Error(val serverError: String) : State<Nothing>()

    companion object {

        /**
         * Returns [State.Loading] instance.
         */
        fun <T> loading() = Loading<T>()

        /**
         * Returns [State.Success] instance.
         * @param data Data to emit with status.
         */
        fun <T> success(data: T) = Success(data)

        /**
         * Returns [State.Error] instance.
         * @param message Description of failure.
         */
        fun <T> error( serverError: String) =
            Error(serverError)
    }
}
