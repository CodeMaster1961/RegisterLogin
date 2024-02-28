package com.example.gameshop.data.repositoryImplementation

import com.example.gameshop.data.ApiService
import com.example.gameshop.data.repository.GameRepository
import com.example.gameshop.data.responses.Game
import retrofit2.Response

class GameRepositoryImplementation (private val api: ApiService) : GameRepository {
    override suspend fun getGames(): List<Game> {
        return api.getGames()
    }
}