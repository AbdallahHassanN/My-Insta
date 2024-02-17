package com.example.recipescompose.presentation.components

import androidx.compose.foundation.background
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
import com.example.myinsta.common.Constants.DEFAULT_User_IMAGE
import com.example.myinsta.common.loadPicture
import com.example.myinsta.components.CustomDivider
import com.example.myinsta.models.User
import com.example.myinsta.presentation.profileScreen.components.RoundedImageView
import com.example.myinsta.ui.theme.Purple80


@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit
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
            user.imageUrl.let { url ->
                val image = loadPicture(
                    url = url,
                    defaultImage = DEFAULT_User_IMAGE
                ).value
                image?.let {
                    RoundedImageView(url)
                }
            }

            Column {
                user.fullName.let { fullName ->
                    Text(
                        text = fullName,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                            .padding(5.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                }

                user.username.let { username ->
                    Text(
                        text = username,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(5.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.DarkGray
                    )
                }
            }
        }
        CustomDivider()
    }
}
