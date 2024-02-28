package com.example.gameshop.ui.screens.profile


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.zIndex
import com.example.gameshop.data.responses.UpdateProfile
import com.example.gameshop.ui.navigation.NavigationDestination
import com.example.gameshop.ui.screens.authenticated.HamburgerMenu
import com.example.gameshop.ui.screens.authenticated.LeftSideNavigationDrawer
import com.example.gameshop.ui.screens.login.AuthenticatedUserState
import com.example.gameshop.ui.screens.login.BottomNavigationBar
import com.example.gameshop.ui.screens.navigationDrawer.LeftSideNavigationDrawer
import com.example.gameshop.ui.theme.GameShopTheme

object ProfileDestination : NavigationDestination {
    override val route: String = "profile"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun UpdateProfileScreen(
    profileViewModel: ProfileViewModel,
    leftSideNavigationDrawer: LeftSideNavigationDrawer,
    navigateToHome: () -> Unit,
    logout: () -> Unit
) {
    val profileState by profileViewModel.profile.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    LeftSideNavigationDrawerProfile(
        profile = profileState,
        onUpdateProfile = profileViewModel::onUpdateProfile,
        navigateToHome = navigateToHome,
        onSubmit = { profileViewModel.updateProfile(profileState) },
        leftSideNavigationDrawer = leftSideNavigationDrawer,
        drawerState = drawerState,
        logout = { logout() },
        navigateToProfile = { /*TODO*/ }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LeftSideNavigationDrawerProfile(
    profile: UpdateProfile,
    onUpdateProfile: (UpdateProfile) -> Unit,
    navigateToHome: () -> Unit,
    onSubmit: () -> Unit,
    leftSideNavigationDrawer: LeftSideNavigationDrawer,
    drawerState: DrawerState,
    logout: () -> Unit,
    navigateToProfile: () -> Unit,
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
                BottomNavigationBar(navigateUp = navigateToHome)
                UpdateProfileForm(
                    profile = profile,
                    onUpdateProfile = onUpdateProfile,
                    navigateToHome = { navigateToHome() },
                    onSubmit = { onSubmit() })
            }
            HamburgerMenu(onClick = {
                leftSideNavigationDrawer.toggleNavigationDrawer(drawerState, scope)
            })
        }
    }
}

@Composable
fun UpdateProfileForm(
    profile: UpdateProfile,
    onUpdateProfile: (UpdateProfile) -> Unit,
    navigateToHome: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color(95, 134, 112)
            ),
            modifier = Modifier
                .size(315.dp, 500.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 10.dp))
            OutlinedTextField(
                value = profile.firstName, onValueChange = {
                    onUpdateProfile(profile.copy(firstName = it))
                }, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(95, 134, 112),
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    focusedContainerColor = Color(95, 134, 112),
                    errorContainerColor = Color(95, 134, 112)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            OutlinedTextField(
                value = profile.lastName, onValueChange = {
                    onUpdateProfile(profile.copy(lastName = it))
                }, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(95, 134, 112),
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    focusedContainerColor = Color(95, 134, 112),
                    errorContainerColor = Color(95, 134, 112)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            OutlinedTextField(
                value = profile.email, onValueChange = {
                    onUpdateProfile(profile.copy(email = it))
                }, colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(95, 134, 112),
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    focusedContainerColor = Color(95, 134, 112),
                    errorContainerColor = Color(95, 134, 112)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Button(onClick = {
                onSubmit()
                navigateToHome()
            }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Save Changes")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateProfileFormPreview() {
    GameShopTheme {
        val profile1 = UpdateProfile("kees", "kaas", "keesvankaas@hotmail.com")
        UpdateProfileForm(
            profile = profile1,
            onUpdateProfile = {},
            onSubmit = {},
            navigateToHome = {}
        )
    }
}