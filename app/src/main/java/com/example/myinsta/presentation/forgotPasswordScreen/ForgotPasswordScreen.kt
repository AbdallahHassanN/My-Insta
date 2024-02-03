package com.example.myinsta.presentation.forgotPasswordScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.myinsta.components.UsernameOrEmailTextField
import com.example.myinsta.ui.theme.QuickSandTypography
import com.example.navapp.Screens

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        LogoImage(image = painterResource(id = R.drawable.lock))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 20.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Trouble Signing in ?",
                fontSize = 20.sp,
                style = QuickSandTypography.labelLarge
            )
        }

        UsernameOrEmailTextField(label = "Enter Email or Username")
        MyButton(text = "Reset My Password",
            onClick = {
                /*TODO Checks */
                navController.navigate(Screens.ChangePasswordScreen.route)
            }
        )
        CenteredText(
            text = "OR",
            color = Color.Gray,
            onClick = {},
            size = 20.sp
        )
        CenteredText(
            text = "Create Account",
            color = Color.Blue,
            onClick = {
                navController.navigate(Screens.RegisterScreen.route)
            },
            size = 20.sp
        )
        CenteredText(
            text = "Back to login",
            color = Color.Blue,
            onClick = {
                navController.navigate(Screens.MainScreen.route)
            },
            size = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayForgotPasswordScreen() {
    val navController = rememberNavController()
    ForgotPasswordScreen(navController = navController)
}