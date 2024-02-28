package com.example.gameshop.ui.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameshop.R
import com.example.gameshop.data.responses.User
import com.example.gameshop.ui.navigation.NavigationDestination
import com.example.gameshop.ui.screens.login.GameShopTopAppBar
import com.example.gameshop.ui.theme.GameShopTheme

object RegisterDestination : NavigationDestination {
    override val route: String = "register"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterForm(
    user: User,
    onValueChange: (User) -> Unit,
    navigateBack: () -> Unit,
    navigateToHome: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFirstNameValid by remember {
        mutableStateOf(false)
    }
    var isLastNameValid by remember {
        mutableStateOf(false)
    }
    var isEmailValid by remember {
        mutableStateOf(false)
    }
    var isPasswordValid by remember {
        mutableStateOf(false)
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    val togglePasswordIcon =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val stringValidator = StringValidator()
    Scaffold(
        topBar = {
            GameShopTopAppBar(canNavigate = true, navigateBack = navigateBack)
        },
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {
            Card(
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(95, 134, 112)
                ),
                modifier = Modifier
                    .size(315.dp, 500.dp)
            ) {
                Text(
                    text = "Register", textAlign = TextAlign.Center, fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.padding(top = 20.dp))
                OutlinedTextField(
                    value = user.firstName, onValueChange = {
                        onValueChange(user.copy(firstName = it))
                        isFirstNameValid = !stringValidator.isFirstNameValid(it)
                    },
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    label = {
                        Text(
                            text = stringResource(R.string.first_name_placeholder),
                            color = Color.White
                        )
                    },
                    isError = isFirstNameValid,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.None,
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(95, 134, 112),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color(95, 134, 112),
                        errorContainerColor = Color(95, 134, 112)
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                    ),
                    trailingIcon = {
                        ErrorIcon(hasInputError = isFirstNameValid)
                    }
                )
                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                OutlinedTextField(
                    value = user.lastName,
                    onValueChange = {
                        onValueChange(user.copy(lastName = it))
                        isLastNameValid = !stringValidator.isLastNameValid(it)
                    },
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    label = {
                        Text(
                            text = stringResource(R.string.last_name_placeholder),
                            color = Color.White
                        )
                    },
                    isError = isLastNameValid,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.None,
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(95, 134, 112),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color(95, 134, 112),
                        errorContainerColor = Color(95, 134, 112)
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                    ),
                    trailingIcon = {
                        ErrorIcon(hasInputError = isLastNameValid)
                    }
                )
                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                OutlinedTextField(
                    value = user.email, onValueChange = {
                        onValueChange(user.copy(email = it))
                        isEmailValid = !stringValidator.isEmailValid(it)
                    },
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    label = {
                        Text(
                            text = stringResource(R.string.email_placeholder),
                            color = Color.White
                        )
                    },
                    isError = isEmailValid,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.None
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(95, 134, 112),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color(95, 134, 112),
                        errorContainerColor = Color(95, 134, 112)
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                    ),
                    trailingIcon = {
                        ErrorIcon(hasInputError = isEmailValid)
                    }
                )
                Spacer(modifier = Modifier.padding(bottom = 15.dp))
                OutlinedTextField(
                    value = user.password,
                    onValueChange = {
                        onValueChange(user.copy(password = it))
                        isPasswordValid = !stringValidator.isPasswordValid(it)
                    },
                    modifier = modifier.align(Alignment.CenterHorizontally),
                    label = {
                        Text(
                            text = stringResource(R.string.password_placeholder),
                            color = Color.White
                        )
                    },
                    isError = isPasswordValid,
                    visualTransformation = togglePasswordIcon,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        if (!isPasswordValid) {
                            PasswordToggle(isPasswordVisible = isPasswordVisible,
                                onToggleClick = {
                                    isPasswordVisible = !isPasswordVisible
                                })
                        }
                        ErrorIcon(hasInputError = isPasswordValid)
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(95, 134, 112),
                        unfocusedIndicatorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        focusedContainerColor = Color(95, 134, 112),
                        errorContainerColor = Color(95, 134, 112)
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 15.sp,
                    ),
                )
                OutlinedButton(
                    onClick = {
                        onSubmit()
                        navigateToHome()
                    }, modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.submit_button), color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun PasswordToggle(isPasswordVisible: Boolean, onToggleClick: () -> Unit) {
    val icon = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

    Box(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember {
                MutableInteractionSource()
            },
            onClick = onToggleClick
        )
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

@Composable
fun ErrorIcon(hasInputError: Boolean) {

    Box {
        if (hasInputError) {
            Icon(imageVector = Icons.Filled.Cancel, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterFormPreview() {
    GameShopTheme {
        val user1 = User("kees", "kaas", "keesvankaas@hotmail.com", "Password1234$")
        RegisterForm(
            user = user1,
            onValueChange = {},
            onSubmit = { /*TODO*/ },
            navigateBack = {},
            navigateToHome = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
