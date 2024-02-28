package com.example.gameshop.data

import com.example.gameshop.data.responses.Login
import com.example.gameshop.data.responses.AuthenticatedMessage
import com.example.gameshop.data.responses.Game
import com.example.gameshop.data.responses.UpdateProfile
import com.example.gameshop.data.responses.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("/users")
    suspend fun createUser(@Body user: User): Response<Unit>

    @POST("/login")
    suspend fun login(@Body user: Login): Response<Unit>

    @GET("/admin")
    suspend fun getAuthenticatedAdminUser(@Header("Authorization") token: String): Response<AuthenticatedMessage>

    @GET("/user")
    suspend fun getAuthenticatedUser(@Header("Authorization") token: String): Response<AuthenticatedMessage>

    @GET("/games")
    suspend fun getGames(): List<Game>

    @PUT("/profile")
    suspend fun updateProfile(@Header("Authorization") token: String,@Body updatedProfile: UpdateProfile): Response<AuthenticatedMessage>
}