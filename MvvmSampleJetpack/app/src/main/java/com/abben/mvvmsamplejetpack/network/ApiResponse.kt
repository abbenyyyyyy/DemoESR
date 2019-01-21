package com.abben.mvvmsamplejetpack.network

import retrofit2.Response

/**
 *  @author abben
 *  on 2019/1/18
 */
sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                if (response.body() == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(response.code(), response.body()!!)
                }
            } else {
                val errorMessage = response.errorBody()?.string()
                ApiErrorResponse(errorMessage ?: "unknown error")
            }
        }
    }
}

/**
 * 当http状态码为204时候为ApiSuccessResponse的body不为空而出现的类
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val code: Int,
    val body: T
) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
