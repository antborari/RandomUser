package com.randomco.testusers.domain

import com.randomco.testusers.data.UserRepository
import com.randomco.testusers.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteUsersUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(user: User) = withContext(Dispatchers.IO) {
        repository.deleteUser(user)
    }
}