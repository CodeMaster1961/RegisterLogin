package com.example.gameshop.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameshop.R
import com.example.gameshop.data.responses.Login
import com.example.gameshop.ui.navigation.NavigationDestination
import com.example.gameshop.ui.screens.register.ErrorIcon
import com.example.gameshop.ui.screens.register.PasswordToggle
import com.example.gameshop.ui.screens.register.StringValidator
import com.example.gameshop.ui.theme.GameShopTheme

object HomeDestination : NavigationDestination {
    override val route: String = "login"
}


@Composable
fun HomeScreen(
    navigateUp: () -> Unit,
    navigateToAuthenticatedScreen: () -> Unit,
    stayOnHomeScreen: () -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val loginState = viewModel.login.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginForm(
            login = loginState.value,
            onValueChange = viewModel::onLoginValueChange,
            onSubmit = {
                if (viewModel.isLoginCredentialsValid(loginState.value) && viewModel.isPasswordCorrect(loginState.value)) {
                    viewModel.login(loginState.value)
                    navigateToAuthenticatedScreen()
                } else {
                    stayOnHomeScreen()
                }
            },
            viewModel = viewModel,
            modifier = modifier
        )
        TextButton(onClick = { navigateUp() }) {
            Text(text = stringResource(R.string.dont_have_account), fontSize = 20.sp)
        }
    }
}


@Composable
fun LoginForm(
    login: Login,
    onValueChange: (Login) -> Unit,
    stringValidator: StringValidator = StringValidator(),
    onSubmit: () -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val togglePasswordIcon =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    var isEmailValid by remember {
        mutableStateOf(false)
    }
    var isPasswordValid by remember {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(95, 134, 112)
            ),
            modifier = Modifier.size(300.dp, 340.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp, start = 10.dp)
            ) {
                Text(
                    text = "Login Form",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                OutlinedTextField(
                    value = login.email,
                    onValueChange = {
                        onValueChange(login.copy(email = it))
                        isEmailValid = !stringValidator.isEmailValid(it)
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.email_placeholder),
                            color = Color.White
                        )
                    },
                    isError = isEmailValid,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(95, 134, 112),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color(95, 134, 112),
                        errorContainerColor = Color(95, 134, 112)
                    ),
                    trailingIcon = {
                        ErrorIcon(hasInputError = isEmailValid)
                    }
                )
                OutlinedTextField(
                    value = login.password,
                    onValueChange = {
                        onValueChange(login.copy(password = it))
                        isPasswordValid = !stringValidator.isPasswordValid(it)
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.password_placeholder),
                            color = Color.White
                        )
                    },
                    isError = isPasswordValid,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(95, 134, 112),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color(95, 134, 112),
                        errorContainerColor = Color(95, 134, 112)
                    ),
                    visualTransformation = togglePasswordIcon,
                    trailingIcon = {
                        if (!isPasswordValid) {
                            PasswordToggle(isPasswordVisible = isPasswordVisible, onToggleClick = {
                                isPasswordVisible = !isPasswordVisible
                            })
                        }
                        ErrorIcon(hasInputError = isPasswordValid)
                    }
                )
                Button(onClick = {
                    onSubmit()
                }, modifier = Modifier.padding(top = 20.dp)) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@Composable
fun ChatScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Chat screen")
        BottomNavigationBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameShopTopAppBar(
    canNavigate: Boolean,
    navigateBack: () -> Unit,
) {
    CenterAlignedTopAppBar(title = { Text(text = "Welcome") },
        navigationIcon = {
            if (canNavigate) {
                IconButton(onClick = {
                    navigateBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun GameShopTopAppBarPreview() {
    GameShopTheme {
        GameShopTopAppBar(true, {})
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar() {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Chat",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email,
            hasNews = false,
            badgeCount = 45
        ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            hasNews = true
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            BadgedBox(
                                badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else if (item.hasNews) {
                                        Badge()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        }
                    )
                }
            }
        }
    ) {

    }
}