package com.example.myinsta.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myinsta.R
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.models.Post
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun ColumnPostCard(
    post: Post,
    onClick: () -> Unit,
    navClick: () -> Unit,
    onLikeClick: () -> Unit,
    isLiked: Boolean,
    img:String
) {
    var isLikedState by remember { mutableStateOf(isLiked) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(
                    bottom = 6.dp,
                    top = 6.dp
                )
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedImageView(img)
            Text(
                text = post.userName, modifier = Modifier.padding(start = 10.dp),
                style = QuickSandTypography.displaySmall,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            post.imageUrl.let { url ->
                val image = loadPicture(
                    url = url,
                    defaultImage = Constants.DEFAULT_User_IMAGE
                ).value
                image?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp) // Set a specific height for the image
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = post.postDescription, modifier = Modifier.padding(start = 10.dp),
                style = QuickSandTypography.displaySmall,
            )
        }
        Row {
            IconButton(onClick = {
                onLikeClick()
                isLikedState = !isLikedState
            }) {
                Icon(
                    imageVector = if (isLikedState) Icons.Filled.Favorite
                    else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLikedState) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            IconButton(
                onClick = navClick,
            ) {
                Icon(
                    painterResource(id = R.drawable.comment_24px),
                    contentDescription = "Comment",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
        CustomDivider()
    }
}