package com.husseinelfeky.moviesexplorer.model

/**
 * Generic class for holding success response, error response and loading status.
 */
data class Result<out T>(
    val status: Status,
    val data: T?,
    val error: Error?,
    val message: String?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    fun requireData(): T {
        return data ?: throw IllegalStateException("Data $data is null.")
    }

    fun requireMessage(): String {
        return message ?: throw IllegalStateException("Message $message is null.")
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): Result<T> {
            return Result(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null, null)
        }
    }
}
