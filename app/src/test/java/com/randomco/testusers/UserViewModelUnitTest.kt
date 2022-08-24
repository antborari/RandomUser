package com.randomco.testusers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.randomco.testusers.domain.DeleteUsersUseCase
import com.randomco.testusers.domain.GetUsersUseCase
import com.randomco.testusers.domain.UpdateUsersUseCase
import com.randomco.testusers.domain.model.User
import com.randomco.testusers.ui.viewmodel.UserViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserViewModelUnitTest {

    @RelaxedMockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @RelaxedMockK
    private lateinit var updateUsersUseCase: UpdateUsersUseCase

    @RelaxedMockK
    private lateinit var deleteUsersUseCase: DeleteUsersUseCase

    private lateinit var usersViewModel: UserViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        usersViewModel = UserViewModel(
            getUsersUseCase,
            updateUsersUseCase,
            deleteUsersUseCase,
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given list of user when usersUseCase only local and viewmodel inits then user flow is equals to flow data`() =
        runTest {
            //Given
            val users = listOf(
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
            coEvery { getUsersUseCase(true) } returns flow { emit(users) }

            //When
            usersViewModel.getUsers(true)

            //Then
            assert(usersViewModel.userModel.value == users)
        }

    @Test
    fun `given list of user when usersUseCase only local and remote and viewmodel inits then user flow is equals to flow data`() =
        runTest {
            //Given
            val users = listOf(
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
            coEvery { getUsersUseCase(false) } returns flow { emit(users) }

            //When
            usersViewModel.getUsers(false)

            //Then
            assert(usersViewModel.userModel.value == users)
        }
}