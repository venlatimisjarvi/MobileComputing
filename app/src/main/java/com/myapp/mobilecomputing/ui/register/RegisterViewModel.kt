package com.myapp.mobilecomputing.ui.register



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.mobilecomputing.data.entity.User
import com.myapp.mobilecomputing.data.repository.UserRepository
import com.myapp.mobilecomputing.Graph
import com.myapp.mobilecomputing.Graph.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository = Graph.userRepository,
): ViewModel() {

    private val _state = MutableStateFlow(UserViewState())

    val state: StateFlow<UserViewState>
        get() = _state

    suspend fun saveUser(user: User): Long {

        return userRepository.addUser(user)

    }

    init {

        viewModelScope.launch {

        }
    }
}
data class UserViewState(
    val users: List<User> = emptyList()
)