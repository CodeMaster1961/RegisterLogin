package com.example.gameshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gameshop.ui.screens.authenticated.AuthenticatedUserScreen
import com.example.gameshop.ui.screens.authenticated.AuthenticatedViewModel
import com.example.gameshop.ui.screens.login.ChatScreen
import com.example.gameshop.ui.screens.login.HomeDestination
import com.example.gameshop.ui.screens.login.HomeScreen
import com.example.gameshop.ui.screens.login.LoginViewModel
import com.example.gameshop.ui.screens.navigationDrawer.LeftSideNavigationDrawer
import com.example.gameshop.ui.screens.profile.ProfileDestination
import com.example.gameshop.ui.screens.profile.ProfileViewModel
import com.example.gameshop.ui.screens.profile.UpdateProfileScreen
import com.example.gameshop.ui.screens.register.RegisterDestination
import com.example.gameshop.ui.screens.register.RegisterForm
import com.example.gameshop.ui.screens.register.RegisterViewModel
import com.example.gameshop.ui.theme.GameShopTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = HomeDestination.route
                    ) {
                        composable(HomeDestination.route) {
                            val homeViewModel = getViewModel<LoginViewModel>()
                            HomeScreen(navigateUp = {
                                navController.navigate(RegisterDestination.route)
                            }, viewModel = homeViewModel, navigateToAuthenticatedScreen = {
                                navController.navigate("authenticated_screen")
                            }, stayOnHomeScreen = {
                                navController.navigate(HomeDestination.route)
                            })
                        }

                        composable("authenticated_screen") {
                            val homeViewModel = getViewModel<LoginViewModel>()
                            val authenticatedViewModel = getViewModel<AuthenticatedViewModel>()
                            val leftSideNavigationDrawer = getViewModel<LeftSideNavigationDrawer>()
                            AuthenticatedUserScreen(
                                gameUiState = authenticatedViewModel.gameUiState,
                                leftSideNavigationDrawer = leftSideNavigationDrawer,
                                viewModel = homeViewModel,
                                logout = {
                                    navController.navigate(HomeDestination.route)
                                },
                                navigateToProfile = {
                                    navController.navigate(ProfileDestination.route)
                                })
                        }

                        composable(ProfileDestination.route) {
                            val profileViewModel = getViewModel<ProfileViewModel>()
                            val leftSideNavigationDrawer = getViewModel<LeftSideNavigationDrawer>()
                            UpdateProfileScreen(
                                profileViewModel = profileViewModel,
                                leftSideNavigationDrawer = leftSideNavigationDrawer,
                                navigateToHome = {
                                    navController.navigate("authenticated_screen")
                                }, logout = {
                                    navController.navigate(HomeDestination.route)
                                })
                        }

                        composable("chat_screen") {
                            ChatScreen()
                        }

                        composable(RegisterDestination.route) {
                            val registerViewModel = getViewModel<RegisterViewModel>()
                            val user = registerViewModel.newUser.collectAsState()
                            RegisterForm(
                                user = user.value,
                                onValueChange = registerViewModel::onValueChange,
                                onSubmit = {
                                    registerViewModel.createUser()
                                },
                                navigateBack = {
                                    navController.popBackStack()
                                },
                                navigateToHome = {
                                    navController.navigate(HomeDestination.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GameShopTheme {

    }
}