package com.example.gameshop.ui.screens.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.gameshop.data.di.getToken
import com.example.gameshop.data.repository.UserRepository
import com.example.gameshop.data.responses.Login
import com.example.gameshop.data.responses.UpdateProfile
import com.example.gameshop.ui.screens.login.AuthenticatedUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

@SuppressLint("StaticFieldLeak")
class ProfileViewModel(private val userRepository: UserRepository, private val context: Context) :
    ViewModel() {
    private val _login = MutableStateFlow(Login("", ""))
    val login: StateFlow<Login> get() = _login
    private val _profile = MutableStateFlow(UpdateProfile("", "", ""))
    val profile: StateFlow<UpdateProfile> get() = _profile

    fun onUpdateProfile(profile: UpdateProfile) {
        _profile.value = profile
    }

    fun updateProfile(profile: UpdateProfile) {
        viewModelScope.launch {
            val response = userRepository.updateProfile(
                "Bearer ${retrieveAuthToken(context)}",
                profile
            )
            Log.d("AUTH TOKEN", "${retrieveAuthToken(context)}")
            if (response.isSuccessful) {
                Log.d("Success", "Profile is successfully updated!")
            } else {
                Log.d("Errors", "Something went wrong ${response.code()}")
                Log.d("Errorsws", "Something went wrong ${response.body()}")
            }
        }
    }

    private fun retrieveAuthToken(context: Context): String? {
        return context.getSharedPreferences("token", Context.MODE_PRIVATE)
            .getString("auth_token", null)
    }
}