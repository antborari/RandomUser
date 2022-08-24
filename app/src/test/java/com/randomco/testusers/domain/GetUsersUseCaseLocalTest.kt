package com.randomco.testusers.domain

import com.randomco.testusers.data.UserRepository
import com.randomco.testusers.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseLocalTest {
    @RelaxedMockK
    private lateinit var repository: UserRepository

    lateinit var updateUsersUseCase: UpdateUsersUseCase
    lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        updateUsersUseCase = UpdateUsersUseCase(repository)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `when database is empty then return empty list`() = runBlocking {
        //Given
        coEvery { repository.getAllUsersFromDatabase() } returns flow { emit(emptyList()) }

        //When
        val response = getUsersUseCase(true)

        //Then
        response.collect {
            assert(it.isEmpty())
        }
    }

    @Test
    fun `when database is not empty then return user`() = runBlocking {
        //Given
        val userList = listOf(
            User(
                "4bd7b051-ffbb-4522-9853-131722620a0e",
                "NMafd",
                "hgffgkplv",
                "nieves.vidal@example.com",
                "https://randomuser.me/api/portraits/men/62.jpg",
                "931-388-696",
                "female",
                "1389 Calle de Argumosa",
                "Palma de Mallorca",
                "Comunidad Valenciana",
                "-63.2454",
                "78.1570",
                "2016-04-11T08:45:43.828Z",
            )
        )
        coEvery { repository.getAllUsersFromDatabase() } returns flow { emit(userList) }

        //When
        val response = getUsersUseCase(true)

        //Then
        response.collect {
            assert(it == userList)
        }
    }
}