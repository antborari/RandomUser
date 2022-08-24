package com.randomco.testusers.domain

import com.randomco.testusers.data.UserRepository
import com.randomco.testusers.data.database.entities.toDatabase
import com.randomco.testusers.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(onlyLocalUsers: Boolean): Flow<List<User>> = flow {
        val localUsers: Flow<List<User>> = repository.getAllUsersFromDatabase()
        if (onlyLocalUsers) {
            emitAll(localUsers)
            return@flow
        }

        val users: List<User> = repository.getAllUsersFromApi()

        if (users.isNotEmpty()) {
            repository.insertUsers(users.map { user -> user.toDatabase() })
            emit(users)
            emitAll(localUsers)
        } else {
            emitAll(localUsers)
        }
    }
}