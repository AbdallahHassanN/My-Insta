package com.example.myinsta.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myinsta.R
import com.example.myinsta.ui.theme.QuickSandTypography
@Preview(showBackground = true)
@Composable
fun NotificationItem(){
    Row(
        Modifier
            .clickable { }
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.hakari),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable {
                    /*TODO Navigate to Source*/
                },
            contentScale = ContentScale.Crop,
            contentDescription = "",
        )
        Text(
            text = "Title",
            Modifier.padding(3.dp),
            style = QuickSandTypography.labelLarge
        )
        Text(
            text = "HELP MY FRIEND PLS PLS PLS",
            Modifier.padding(3.dp),
            color = Color.Gray,
            style = QuickSandTypography.labelMedium
        )
    }
}