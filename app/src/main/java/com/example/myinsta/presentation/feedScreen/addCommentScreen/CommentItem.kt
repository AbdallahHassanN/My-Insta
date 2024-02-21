package com.example.myinsta.presentation.feedScreen.addCommentScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myinsta.common.timeFormat
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.models.Comment
import com.example.myinsta.ui.theme.QuickSandTypography


@Composable
fun CommentItem(
    comment: Comment
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = comment.authorName,
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                    fontSize = 20.sp,
                    style = QuickSandTypography.labelLarge,
                    )
                Text(
                    text = timeFormat(comment.time),
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                    color = Color.DarkGray,
                    fontSize = 10.sp,
                    style = QuickSandTypography.labelLarge
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = comment.comment,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 5.dp, bottom = 5.dp)
                        .fillMaxWidth(0.9f),
                    fontSize = 15.sp,
                    style = QuickSandTypography.labelLarge,
                )
                IconButton(
                    onClick = {
                        /*TODO Handle in viewModel */
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Like",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(10.dp)
                    )
                }
            }
            CustomDivider()
        }
    }
}
