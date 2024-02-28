package com.example.gameshop.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfile(
    val firstName: String,
    val lastName: String,
    val email: String
)