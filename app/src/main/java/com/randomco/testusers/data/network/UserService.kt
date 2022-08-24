package com.randomco.testusers.data.network

import com.randomco.testusers.data.model.UserModel
import javax.inject.Inject

class UserService @Inject constructor(private val api: UserApiClient) {
    suspend fun getUsers(): List<UserModel> {
        val response = api.getUsers()
        return response.body()?.results ?: emptyList()
    }
}