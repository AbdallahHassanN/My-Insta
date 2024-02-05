package com.example.myinsta.presentation.changePasswordScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myinsta.R
import com.example.myinsta.components.LogoImage
import com.example.myinsta.components.MyButton
import com.example.myinsta.components.PasswordTextField
import com.example.myinsta.presentation.forgotPasswordScreen.ForgotPasswordScreen
import com.example.navapp.Screens

@Composable
fun ChangePasswordScreen(navController: NavController){
    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        LogoImage(image = painterResource(id = R.drawable.lock))
        PasswordTextField("Enter Password")
        PasswordTextField("Re-Type Password")
        MyButton(text = "Reset My Password",
            onClick = {
                /*TODO Checks */
                navController.navigate(Screens.MainScreen.route)
            }
        )
    }*/
}

@Preview(showBackground = true)
@Composable
fun DisplayChangePasswordScreen() {
    val navController = rememberNavController()
    ChangePasswordScreen(navController = navController)
}