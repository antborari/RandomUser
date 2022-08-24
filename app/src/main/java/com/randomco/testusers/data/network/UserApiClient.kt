package com.randomco.testusers.data.network

import com.randomco.testusers.data.model.ResultModel
import com.randomco.testusers.util.DEFAULT_GET_USERS_OFFSET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiClient {
    @GET("api")
    suspend fun getUsers(@Query("results") count: Int = DEFAULT_GET_USERS_OFFSET): Response<ResultModel>
}