package com.randomco.testusers.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.randomco.testusers.domain.DeleteUsersUseCase
import com.randomco.testusers.domain.GetUsersUseCase
import com.randomco.testusers.domain.UpdateUsersUseCase
import com.randomco.testusers.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val updateUsersUseCase: UpdateUsersUseCase,
    private val deleteUsersUseCase: DeleteUsersUseCase,
) : ViewModel() {

    private val _userModel = MutableStateFlow<List<User>>(listOf())
    val userModel: StateFlow<List<User>>
        get() = _userModel

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        getUsers(true)
    }

    fun getUsers(onlyLocalUsers: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            getUsersUseCase(onlyLocalUsers).collect { items ->
                _userModel.emit(items)
                _isLoading.value = false
            }
        }
    }

    fun getFavourites(): List<User> {
        return userModel.value.filter { item -> item.isFavourite }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            updateUsersUseCase(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.Main) {
            deleteUsersUseCase(user)
        }
    }
}