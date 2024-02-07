package com.example.myinsta.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun CenteredText(
    text:String,
    color:Color,
    onClick:() -> Unit,
    size:TextUnit
){
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = color,
            fontSize = size,
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = remember { null }
                ) { onClick() },
            style = QuickSandTypography.labelLarge
        )
    }
}