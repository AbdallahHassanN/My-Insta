package com.example.myinsta.presentation.profileScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun RoundedImageView(
    image: Painter
) {

    Image(
        painter = image,
        contentDescription = null, // Provide a meaningful description
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape), // Adjust the corner radius as needed
        contentScale = ContentScale.Crop
    )
}