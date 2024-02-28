package com.example.gameshop.ui.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameshop.data.repository.UserRepository
import com.example.gameshop.data.responses.AuthenticatedMessage
import com.example.gameshop.data.responses.Login
import com.example.gameshop.ui.screens.register.StringValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
class HomeViewModel constructor(private val userRepository: UserRepository, private val context: Context) : ViewModel() {
    private val _login = MutableStateFlow(Login("", ""))
    private val _userState =
        MutableStateFlow<AuthenticatedUserState>(AuthenticatedUserState.Loading)
    private val stringValidator = StringValidator()
    val login: StateFlow<Login> get() = _login
    val userState: StateFlow<AuthenticatedUserState> get() = _userState


    fun onLoginValueChange(login: Login) {
        _login.value = login
    }

    fun isLoginCredentialsValid(user: Login): Boolean {
        return stringValidator.isEmailValid(user.email) && stringValidator.isPasswordValid(user.password)
    }

    fun toggleNavigationDrawer(drawerState: DrawerState, scope: CoroutineScope) {
        scope.launch {
            drawerState.apply {
                if (drawerState.isClosed) open() else close()
            }
        }
    }

    fun isPasswordCorrect(user: Login): Boolean {
        viewModelScope.launch {
            val response = userRepository.login(user)
            if (!response.isSuccessful) {
                throw HttpException(response)
            }
        }
        return true
    }

    fun login(user: Login) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(user)
                if (response.isSuccessful) {
                    val token = response.headers()["set-Cookie"]
                    val sessionIdToken = fetchSessionIdToken(token)
                    saveAuthToken(sessionIdToken)
                    fetchAuthenticatedUser("Bearer $sessionIdToken")
                    Log.d("cookie", "$token")
                    Log.d("saved", "Auth Token: ${getAuthToken()}")
                    Log.d("Success", "Login successfully with status code: ${response.code()}")
                } else {
                    Log.d("Error", "Login failed with status code: ${response.code()}")
                }
            } catch (error: HttpException) {
                Log.d("Error", error.message ?: "Unknown error")
            }
        }
    }

    private fun saveAuthToken(token: String?) {
        context.getSharedPreferences("token", Context.MODE_PRIVATE)
            .edit {
                putString("auth_token", token)
            }
    }

    private fun getAuthToken(): String? {
        return context.getSharedPreferences("token", Context.MODE_PRIVATE)
            .getString("auth_token", null)
    }

    private fun fetchSessionIdToken(cookieHeader: String?): String? {
        if (cookieHeader != null) {
            val sessionIdStartIndex = cookieHeader.indexOf("session-id=")
            val sessionIdEndIndex = cookieHeader.indexOf(";", startIndex = sessionIdStartIndex)
            if (sessionIdStartIndex != -1 && sessionIdEndIndex != -1) {
                return cookieHeader.substring(
                    sessionIdStartIndex + "session-id=".length,
                    sessionIdEndIndex
                )
            }
        }
        return null
    }

    private fun fetchAuthenticatedUser(token: String?) {
        viewModelScope.launch {
            try {
                if (token != null) {
                    hasUserRole(token)
                    hasAdminRole(token)
                }
            } catch (error: Exception) {
                Log.d("Error", error.message ?: "Unknown error")
                _userState.value = AuthenticatedUserState.Error
            }
        }
    }

    private suspend fun hasAdminRole(token: String) {
        val adminResponse = userRepository.getAuthenticatedAdminDetails(token)
        val adminDetails = adminResponse.body()
        try {
            if (adminResponse.isSuccessful) {
                _userState.value = AuthenticatedUserState.Success(adminDetails)
            }
        } catch (error: HttpException) {
            _userState.value = AuthenticatedUserState.Error
        }
    }

    private suspend fun hasUserRole(token: String) {
        val userResponse = userRepository.getAuthenticatedUserDetails(token)
        val userDetails = userResponse.body()
        try {
            if (userResponse.isSuccessful) {
                _userState.value = AuthenticatedUserState.Success(userDetails)
            }
        } catch (error: HttpException) {
            _userState.value = AuthenticatedUserState.Error
        }
    }
}

sealed class AuthenticatedUserState {
    data class Success(val user: AuthenticatedMessage?) : AuthenticatedUserState()
    data object Loading : AuthenticatedUserState()
    data object Error : AuthenticatedUserState()
}