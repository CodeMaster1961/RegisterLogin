package com.example.gameshop.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gameshop.data.responses.User
import kotlinx.coroutines.flow.MutableStateFlow

class StringValidator : ViewModel() {

    private val _user = MutableStateFlow(User("", "", "", ""))
    private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
    private val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#\$%^&+=])([A-Za-z\\d@#\$%^&+=]){8,32}\$")

    var isFirstNameValid by mutableStateOf(false)
    var isLastNameValid by mutableStateOf(false)
    var isEmailValid by mutableStateOf(false)
    var isPasswordValid by mutableStateOf(false)


    fun isFirstNameValid(firstName: String): Boolean {
        return firstName.length in 1..10
    }

    fun isLastNameValid(lastName: String): Boolean {
        return lastName.length in 1..10
    }

    fun isEmailValid(email: String): Boolean {
        return email.matches(emailRegex)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.matches(passwordRegex)
    }
}