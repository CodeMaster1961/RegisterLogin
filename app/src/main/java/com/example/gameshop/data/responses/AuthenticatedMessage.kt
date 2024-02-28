package com.example.gameshop.data.responses


import kotlinx.serialization.Serializable


@Serializable
data class AuthenticatedMessage(
    val message: String
)