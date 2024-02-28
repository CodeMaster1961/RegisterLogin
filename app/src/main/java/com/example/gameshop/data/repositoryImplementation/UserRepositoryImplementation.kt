package com.example.gameshop.data.repositoryImplementation

import com.example.gameshop.data.ApiService
import com.example.gameshop.data.repository.UserRepository
import com.example.gameshop.data.responses.AuthenticatedMessage
import com.example.gameshop.data.responses.Login
import com.example.gameshop.data.responses.UpdateProfile
import com.example.gameshop.data.responses.User
import retrofit2.Response

class UserRepositoryImplementation(private val api: ApiService) : UserRepository {
    override suspend fun createUser(user: User): Response<Unit> {
       return api.createUser(user)
    }

    override suspend fun login(user: Login): Response<Unit> {
       return api.login(user)
    }

    override suspend fun getAuthenticatedAdminDetails(token: String): Response<AuthenticatedMessage> {
        return api.getAuthenticatedAdminUser(token)
    }

    override suspend fun getAuthenticatedUserDetails(token: String): Response<AuthenticatedMessage> {
        return api.getAuthenticatedUser(token)
    }

    override suspend fun updateProfile(token: String,updatedProfile: UpdateProfile): Response<AuthenticatedMessage> {
        return api.updateProfile(token,updatedProfile)
    }
}