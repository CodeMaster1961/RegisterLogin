package com.example.gameshop.data.repository

import com.example.gameshop.data.responses.Login
import com.example.gameshop.data.responses.AuthenticatedMessage
import com.example.gameshop.data.responses.UpdateProfile
import com.example.gameshop.data.responses.User
import retrofit2.Response
import retrofit2.http.Body

interface UserRepository {
    suspend fun createUser(user: User): Response<Unit>

    suspend fun login(user: Login): Response<Unit>

    suspend fun getAuthenticatedAdminDetails(token: String): Response<AuthenticatedMessage>

    suspend fun getAuthenticatedUserDetails(token: String): Response<AuthenticatedMessage>

    suspend fun updateProfile(token: String,updatedProfile: UpdateProfile): Response<AuthenticatedMessage>
}