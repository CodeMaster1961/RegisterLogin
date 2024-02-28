package com.example.gameshop.data.repository

import com.example.gameshop.data.responses.Game
import retrofit2.Response

interface GameRepository {
    suspend fun getGames(): List<Game>
}