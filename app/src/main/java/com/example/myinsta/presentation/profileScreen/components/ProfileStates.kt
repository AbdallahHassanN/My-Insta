package com.example.myinsta.presentation.profileScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun ProfileStates(
    text :String,
    numberText :String,
    onClick:()->Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 5.dp, start = 10.dp)
            .clickable {
                onClick()
            }
    ) {
        Text(text = text,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = QuickSandTypography.headlineMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = numberText,
            color = Color.Black,
            style = QuickSandTypography.headlineMedium
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DisplayProfileState(){
    ProfileStates(text = "Follower", numberText = "64") {
    }
}