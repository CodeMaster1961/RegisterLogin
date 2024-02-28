package com.example.gameshop.ui.screens.profile


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gameshop.data.responses.UpdateProfile
import com.example.gameshop.ui.navigation.NavigationDestination
import com.example.gameshop.ui.screens.login.AuthenticatedUserState
import com.example.gameshop.ui.theme.GameShopTheme

object ProfileDestination : NavigationDestination {
    override val route: String = "profile"
}

@Composable
fun UpdateProfileScreen(
    profileViewModel: ProfileViewModel,
    navigateToHome: () -> Unit
) {
    val profileState by profileViewModel.profile.collectAsState()
    val userLoginState by profileViewModel.login.collectAsState()
    UpdateProfileForm(
        profile = profileState,
        onUpdateProfile = profileViewModel::onUpdateProfile,
        onSubmit = {
            profileViewModel.updateProfile(profileState)
        },
        navigateToHome = navigateToHome
    )
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