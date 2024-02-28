package com.example.gameshop.ui.screens.authenticated

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gameshop.data.repository.GameRepository
import com.example.gameshop.data.responses.Game
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed interface GameUiState {
    data class Success(val games: List<Game>) : GameUiState
    data object Loading : GameUiState
    data object Error : GameUiState
}

class AuthenticatedViewModel(private val gameRepository: GameRepository) : ViewModel() {

    var gameUiState: GameUiState by mutableStateOf(GameUiState.Loading)

    init {
        getGames()
    }

    private fun getGames() {
        viewModelScope.launch {
            gameUiState = GameUiState.Loading
            gameUiState = try {
                GameUiState.Success(gameRepository.getGames())
            } catch (error: IOException) {
                GameUiState.Error
            } catch (error: HttpException) {
                GameUiState.Error
            }
        }
    }

}