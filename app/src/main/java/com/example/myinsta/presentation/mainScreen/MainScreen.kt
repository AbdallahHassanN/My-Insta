package com.example.myinsta.presentation.mainScreen


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.R
import com.example.myinsta.common.Constants
import com.example.myinsta.common.Constants.TAG
import com.example.myinsta.components.CenteredText
import com.example.myinsta.components.LogoImage
import com.example.myinsta.components.MyButton
import com.example.myinsta.components.PasswordTextField
import com.example.myinsta.components.TextAndLink
import com.example.myinsta.components.UsernameOrEmailTextField
import com.example.myinsta.response.Resource
import com.example.navapp.Screens

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainScreenViewModel = hiltViewModel()
    val emailValue by viewModel.email.collectAsStateWithLifecycle()
    val passwordValue by viewModel.password.collectAsStateWithLifecycle()
    val signInValue = viewModel.signInState.collectAsState()
    val context = LocalContext.current

    signInValue.value.let {
        when (it) {
            is Resource.Error -> {
                LaunchedEffect(Unit) {
                    showMessage(context, "Invalid email or password")
                }
            }

            is Resource.Loading -> {
                LaunchedEffect(Unit) {
                    Log.d(TAG, "Loading")
                }
            }

            is Resource.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(Screens.FeedScreen.route) {
                        popUpTo(Screens.MainScreen.route) { inclusive = true }
                    }
                }
            }

            else -> {
                Log.d(Constants.TAG, "Unexpected Error")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoImage(
            painterResource(id = R.drawable.instagram_text)
        )

        UsernameOrEmailTextField(
            label = "Enter Username or Email",
            inputValue = emailValue,
            onQueryChanged = { viewModel.onEmailChanged(it) },
            isError = viewModel.emailValidation.value?.isSuccess?.not() ?: false,
        )
        PasswordTextField(
            label = "Enter Password",
            inputValue = passwordValue,
            onQueryChanged = { viewModel.onPasswordChanged(it) },
            isError = viewModel.passwordValidation.value?.isSuccess?.not() ?: false,
        )

        MyButton(text = "Login", onClick = {
            viewModel.signIn()
        })
        CenteredText(
            text = "OR", color = Color.Gray, onClick = {}, size = 20.sp
        )
        CenteredText(
            text = "Forgot Password ?", color = Color.Black, onClick = {
                navController.navigate(Screens.ForgotPasswordScreen.route)
            }, size = 20.sp
        )
        TextAndLink(
            onClick = {
                navController.navigate(Screens.RegisterScreen.route)
            }, text = "Have an account ?", link = " Register Here"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayMainScreen() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}

fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}