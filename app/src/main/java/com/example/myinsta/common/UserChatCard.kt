package com.example.myinsta.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.models.ChatUserList
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView

@Composable
fun UserChatCard(
    onClick: () -> Unit,
    chatItem: ChatUserList,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Row {
            chatItem.imgUrl.let { url ->
                val image = loadPicture(
                    url = url,
                    defaultImage = Constants.DEFAULT_User_IMAGE
                ).value
                image?.let {
                    RoundedImageView(url)
                }
            }
            Column {
                chatItem.friendUsername.let { username ->
                    Text(
                        text = username,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .padding(5.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                }
                Row {
                    chatItem.lastMessage.let { lastMessage ->
                        Text(
                            text = lastMessage,
                            Modifier
                                .padding(5.dp)
                                .fillMaxWidth(0.5F),
                            color = Color.DarkGray
                        )
                    }
                    chatItem.lastMessageTime.let {
                        Text(
                            text = timeFormat(it),
                            Modifier.padding(5.dp),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
        CustomDivider()
    }
}
