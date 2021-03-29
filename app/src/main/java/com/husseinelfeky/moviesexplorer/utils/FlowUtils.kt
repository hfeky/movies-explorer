package com.husseinelfeky.moviesexplorer.utils

import androidx.lifecycle.MutableLiveData
import com.husseinelfeky.moviesexplorer.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

/**
 * Collect the [Flow] to a [MutableLiveData].
 */
suspend fun <T> Flow<T>.collectTo(liveData: MutableLiveData<T>) {
    collect { liveData.value = it }
}

/**
 * Wrap the response with a [Result] and handle success, error and loading status.
 */
suspend fun <T> FlowCollector<Result<T>>.getResult(
    defaultErrorMessage: String? = null,
    request: suspend () -> Response<T>
) {
    emit(Result.loading())

    try {
        val response = request.invoke()

        if (response.isSuccessful) {
            emit(Result.success(response.body()))
        } else {
            if (!defaultErrorMessage.isNullOrEmpty()) {
                emit(Result.error(defaultErrorMessage, null))
            } else {
                throw UnknownError()
            }
        }
    } catch (e: IOException) {
        emit(Result.error("No internet connection is available.", Error(e)))
    } catch (e: Throwable) {
        emit(Result.error("An unknown error occurred.", Error(e)))
    }
}

/**
 * Map the [Flow] result to another result.
 */
fun <T, R> Flow<Result<T>>.mapResultTo(mapper: (T?) -> R?): Flow<Result<R>> {
    return map {
        when (it.status) {
            Result.Status.LOADING -> Result.loading(mapper.invoke(it.data))
            Result.Status.SUCCESS -> Result.success(mapper.invoke(it.data))
            Result.Status.ERROR -> Result.error(it.message!!, it.error)
        }
    }
}
