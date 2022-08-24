package com.randomco.testusers.data

import com.randomco.testusers.data.database.dao.UserDao
import com.randomco.testusers.data.database.entities.UserEntity
import com.randomco.testusers.data.model.UserModel
import com.randomco.testusers.data.network.UserService
import com.randomco.testusers.domain.model.User
import com.randomco.testusers.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserService,
    private val userDao: UserDao
) {
    suspend fun getAllUsersFromApi(): List<User> {
        val response: List<UserModel> = api.getUsers()
        return response.map { it.toDomain() }
    }

    fun getAllUsersFromDatabase(): Flow<List<User>> {
        val response = userDao.getAllUser()
        return response.map { items -> items.map { it.toDomain() } }
    }

    suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertAllUsers(users)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.uuid)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user.uuid, user.isFavourite)
    }
}