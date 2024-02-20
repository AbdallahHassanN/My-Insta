package com.example.myinsta.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myinsta.common.Constants.DEFAULT_User_IMAGE
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.models.Post
import com.example.myinsta.models.User
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView


@Composable
fun GridPostsCard(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Row {
            post.imageUrl.let { url ->
                val image = loadPicture(
                    url = url,
                    defaultImage = DEFAULT_User_IMAGE
                ).value
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .border(1.dp, Color.Black),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
