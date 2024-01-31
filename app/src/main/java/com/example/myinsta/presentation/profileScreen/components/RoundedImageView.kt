package com.example.myinsta.presentation.profileScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myinsta.R
import com.example.myinsta.components.LogoImage

@Composable
fun RoundedImageView(
    image: Painter
) {

    Image(
        painter = image,
        contentDescription = null, // Provide a meaningful description
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape), // Adjust the corner radius as needed
        contentScale = ContentScale.Crop
    )
}