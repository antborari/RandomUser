package com.randomco.testusers.domain

import com.randomco.testusers.data.UserRepository
import com.randomco.testusers.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseApiTest {

    @RelaxedMockK
    private lateinit var repository: UserRepository

    lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getUsersUseCase = GetUsersUseCase(repository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        //Given
        coEvery { repository.getAllUsersFromApi() } returns emptyList()

        //When
        val response = getUsersUseCase(false)

        //Then
        response.collect {
            coVerify(exactly = 1) { repository.getAllUsersFromDatabase() }
        }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
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
        coEvery { repository.getAllUsersFromApi() } returns userList

        //When
        val response = getUsersUseCase(false)

        //Then
        response.collect {
            assert(it == userList)
        }
    }
}