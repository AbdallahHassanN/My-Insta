package com.example.myinsta.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun TextAndLink(
    onClick: () -> Unit,
    text: String,
    link: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                start = 20.dp,
                end = 20.dp
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = QuickSandTypography.labelLarge
        )
        Text(
            text = link,
            color = Color.Blue,
            modifier = Modifier.clickable { onClick() },
            style = QuickSandTypography.labelLarge
        )
    }
}