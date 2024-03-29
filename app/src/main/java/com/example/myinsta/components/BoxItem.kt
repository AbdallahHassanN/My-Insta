package com.example.myinsta.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun BoxItem(
    text: String,
    onClick: () -> Unit,
    color: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = remember { null }
            ) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = color,
            modifier = Modifier.weight(1f),
            style = QuickSandTypography.bodyLarge
        )
        Icon(
            imageVector = Icons.Outlined.ArrowForward,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
    }
}