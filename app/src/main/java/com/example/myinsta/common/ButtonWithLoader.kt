package com.example.myinsta.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun ButtonWithLoader(
    buttonText: String,
    showLoader: Boolean,
    onClick: () -> Unit,
) {
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
            if (showLoader) {
                CircularProgressIndicator(color = Green, modifier = Modifier.size(28.dp))
            } else {
                Text(
                    text = buttonText,
                    style = QuickSandTypography.displayMedium
                )
            }
        }
    }
}

