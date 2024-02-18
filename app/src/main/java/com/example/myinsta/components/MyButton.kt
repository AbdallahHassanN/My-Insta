package com.example.myinsta.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.myinsta.ui.theme.QuickSandTypography

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyButton(
    text:String,
    onClick:() -> Unit
){
    val keyboardController = LocalSoftwareKeyboardController.current

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
        Button(
            onClick = {
                onClick()
                keyboardController?.hide()
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults
                .buttonColors(containerColor = Color.Blue), // Set your desired background color

        ) {
            Text(
                text = text,
                style = QuickSandTypography.displayMedium
            )
        }
    }
}