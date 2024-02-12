package com.example.myinsta.presentation.profileScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.myinsta.common.Constants.DEFAULT_User_IMAGE
import com.example.myinsta.common.loadPicture
import com.example.myinsta.models.User

@Composable
fun RoundedImageView(
    img: String
) {


    img.let {
        val image = loadPicture(url = it, defaultImage = DEFAULT_User_IMAGE)
            .value
        image?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null, // Provide a meaningful description
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape), // Adjust the corner radius as needed
                contentScale = ContentScale.Crop
            )
        }
    }
}