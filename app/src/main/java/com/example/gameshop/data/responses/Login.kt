package com.example.gameshop.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class Login(val email: String, val password: String)
