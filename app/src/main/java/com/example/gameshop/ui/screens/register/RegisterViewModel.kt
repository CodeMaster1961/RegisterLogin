package com.example.gameshop.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameshop.data.repository.UserRepository
import com.example.gameshop.data.responses.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableStateFlow(User("", "", "", ""))
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
    private val passwordRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#\$%^&+=])([A-Za-z\\d@#\$%^&+=]){8,32}\$")
    val newUser: StateFlow<User> get() = _user

    fun onValueChange(user: User) {
        _user.value = user
    }

    private val stringValidator = StringValidator()

    private fun isRegisterFormValid(): Boolean {
        val firstName = stringValidator.isFirstNameValid(_user.value.firstName)
        val lastName = stringValidator.isLastNameValid(_user.value.lastName)
        val email = stringValidator.isEmailValid(_user.value.email)
        val password = stringValidator.isPasswordValid(_user.value.password)
        return firstName && lastName && email && password
    }

    fun createUser() {
        viewModelScope.launch {
            try {
                val registerForm = isRegisterFormValid()
                if (registerForm) {
                    userRepository.createUser(_user.value)
                }
            } catch (error: HttpException) {
                println(error.message!!)
            }
        }
    }
}