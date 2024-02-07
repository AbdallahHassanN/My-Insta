package com.example.myinsta.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myinsta.R
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.ui.theme.QuickSandTypography
@Preview(showBackground = true)
@Composable
fun PostOnFeed(
) {
    var isLiked by remember { mutableStateOf(false) }

    Column(
        Modifier
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .clickable {},
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedImageView(painterResource(id = R.drawable.hakari))
            Text(
                text = "Username", modifier = Modifier.padding(start = 10.dp),
                style = QuickSandTypography.displaySmall,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.hakari),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row {
            IconButton(onClick = {
                /*TODO Handle in viewModel */
                isLiked = !isLiked
            }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite
                    else Icons.Filled.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            IconButton(
                onClick = {
                    /*TODO Comment */
                },
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
    }
}