package com.example.myinsta.presentation.mainScreen


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.R
import com.example.myinsta.common.CircularProgressBar
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
    val snackBarHostState = remember { SnackbarHostState() }
    val loading = viewModel.loading.value

    // Function to show the snackBar
    suspend fun showSnackBar(message: String) {
        snackBarHostState.showSnackbar(message)
    }

    signInValue.value.let {
        when (it) {
            is Resource.Error -> {
                LaunchedEffect(Unit) {
                    val error = it.message
                    showSnackBar(error.toString())
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
                Log.d(TAG, "Unexpected Error")
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
            label = "Enter Email",
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
        TextAndLink(
            onClick = {
                navController.navigate(Screens.RegisterScreen.route)
            }, text = "Have an account ?", link = " Register Here"
        )
        CircularProgressBar(isDisplayed = loading)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp), // Adjust padding as needed
            contentAlignment = Alignment.BottomCenter
        ) {
            SnackbarHost(
                hostState = snackBarHostState,
            ) { snackBar ->
                Snackbar(
                    snackbarData = snackBar,
                    containerColor = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayMainScreen() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}
