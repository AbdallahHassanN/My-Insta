package com.example.myinsta.presentation.mainScreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.R
import com.example.myinsta.components.CenteredText
import com.example.myinsta.components.LogoImage
import com.example.myinsta.components.MyButton
import com.example.myinsta.components.PasswordTextField
import com.example.myinsta.components.TextAndLink
import com.example.myinsta.components.UsernameOrEmailTextField
import com.example.navapp.Screens

@Composable
fun MainScreen(navController: NavController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogoImage(
            painterResource(id = R.drawable.instagram_text)
        )

        UsernameOrEmailTextField("Enter Username or Email")


        PasswordTextField("Enter Password")

        MyButton(
            text = "Login",
            onClick = {
                /*TODO Check */
                navController.navigate(Screens.FeedScreen.route)
            }
        )
        CenteredText(
            text = "OR",
            color = Color.Gray,
            onClick = {},
            size = 20.sp
        )
        CenteredText(
            text = "Forgot Password ?",
            color = Color.Black,
            onClick = {
                navController.navigate(Screens.ForgotPasswordScreen.route)
            },
            size = 20.sp
        )
        TextAndLink(
            onClick = {
                navController.navigate(Screens.RegisterScreen.route)
            },
            text = "Have an account ?",
            link = " Register Here"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayMainScreen() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}