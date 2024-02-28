package com.example.gameshop.ui.screens.authenticated

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gameshop.data.responses.Game
import com.example.gameshop.ui.navigation.NavigationDestination
import com.example.gameshop.ui.screens.login.AuthenticatedUserState
import com.example.gameshop.ui.screens.login.BottomNavigationBar
import com.example.gameshop.ui.screens.login.LoginViewModel
import com.example.gameshop.ui.screens.navigationDrawer.LeftSideNavigationDrawer
import com.example.gameshop.ui.theme.GameShopTheme

object AuthenticatedDestination : NavigationDestination {
    override val route: String = "authenticated"
}

@Composable
fun AuthenticatedUserScreen(
    gameUiState: GameUiState,
    leftSideNavigationDrawer: LeftSideNavigationDrawer,
    viewModel: LoginViewModel,
    navigateToProfile: () -> Unit,
    logout: () -> Unit
) {
    val userState by viewModel.userState.collectAsState()
    when (val currentState = userState) {
        is AuthenticatedUserState.Success -> {
            val user = currentState.user
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            Box(modifier = Modifier.fillMaxSize()) {
                if (user != null) {
                    LeftSideNavigationDrawer(
                        leftSideNavigationDrawer = leftSideNavigationDrawer,
                        drawerState = drawerState,
                        logout = logout,
                        navigateToProfile = navigateToProfile,
                        welcomeMessage = user.message
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top
                ) {
                }
            }
        }

        is AuthenticatedUserState.Error -> {
            Text(
                text = "Error fetching user details",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        else -> {}
    }
}


@Composable
fun HamburgerMenu(onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.DensityMedium,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 10.dp, top = 15.dp)
                .clickable { onClick() }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LeftSideNavigationDrawer(
    leftSideNavigationDrawer: LeftSideNavigationDrawer,
    drawerState: DrawerState,
    logout: () -> Unit,
    navigateToProfile: () -> Unit,
    welcomeMessage: String?,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        modifier = Modifier.background(Color.Gray),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(modifier = modifier, contentAlignment = Alignment.TopStart) {
                    IconButton(onClick = { navigateToProfile() }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp, 100.dp)
                        )
                    }
                    Text(
                        text = "Profile",
                        modifier = Modifier.padding(start = 50.dp, top = 10.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { logout() },
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                    ) {
                        Text(text = "Logout")
                    }
                }
            }
        }) {
        Scaffold {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                BottomNavigationBar({})
                if (welcomeMessage != null) {
                    Text(
                        text = welcomeMessage,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(top = 10.dp, end = 10.dp)
                    )
                }
            }
            HamburgerMenu(onClick = {
                leftSideNavigationDrawer.toggleNavigationDrawer(drawerState, scope)
            })
        }
    }
}

@Composable
fun AuthenticatedGameListScreen(
    gameUiState: GameUiState,
    navigateUp: () -> Unit
) {
    when (gameUiState) {
        is GameUiState.Success -> {
            AuthenticatedBody(games = gameUiState.games)
//            BottomNavigationBar(navigateToChat = navigateUp)
        }

        else -> {}
    }
}

@Composable
fun AuthenticatedBody(
    games: List<Game>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (games.isEmpty()) {
            Text(
                text = "There are no games found",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            GameList(games = games)
        }
    }
}

@Composable
fun GameList(games: List<Game>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(games) { game ->
            GameCard(game = game)
        }
    }
}

@Composable
fun GameCard(
    game: Game
) {
    Column {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceTint
            ),
            modifier = Modifier
                .size(width = 240.dp, height = 260.dp)
                .padding(top = 15.dp, end = 15.dp, start = 15.dp)
        ) {
            Text(
                text = game.gameTitle,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            AsyncImage(
                model = game.gameImage,
                contentDescription = "image",
                modifier = Modifier.size(width = 240.dp, height = 100.dp)
            )
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Text(text = "$" + game.gamePrice.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameCardPreview() {
    GameShopTheme {
        val game1 = Game(
            1,
            "Call of Duty Black Ops III",
            "best cod of all time",
            "https://assets1.ignimgs.com/2015/09/01/callofdutyblackops3-buttonjpg-03c985.jpg",
            29.99
        )
        GameCard(game = game1)
    }
}

@Preview(showBackground = true)
@Composable
fun GameListPreview() {
    val game1 = Game(
        1,
        "Call of Duty Black Ops III",
        "best cod of all time",
        "https://assets1.ignimgs.com/2015/09/01/callofdutyblackops3-buttonjpg-03c985.jpg",
        29.99
    )
    val game2 = Game(
        2,
        "Call of Duty Black Ops II",
        "best cod of all time",
        "https://images6.alphacoders.com/447/447007.jpg",
        29.99
    )
    GameShopTheme {
        GameList(games = listOf(game1, game2))
    }
}