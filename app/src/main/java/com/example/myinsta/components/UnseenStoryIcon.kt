package com.example.myinsta.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myinsta.R
import com.example.myinsta.ui.theme.StoryColor1
import com.example.myinsta.ui.theme.StoryColor2

@Composable
fun UnseenStoryIcon() {

    val gradientColors = listOf(
        StoryColor1,
        StoryColor2,
    )
    Image(
        painter = painterResource(id = R.drawable.hakari),
        contentDescription = null, // Provide a meaningful description
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .border(
                2.dp, Brush.radialGradient(gradientColors),
                shape = CircleShape
            )
            .clickable {},
        // Adjust the corner radius as needed
        contentScale = ContentScale.Crop,
    )
}
