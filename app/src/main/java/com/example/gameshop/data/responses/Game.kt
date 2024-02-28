package com.example.gameshop.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val gameId: Int,
    val gameTitle: String,
    val gameDescription: String,
    val gameImage: String,
    val gamePrice: Double
)
