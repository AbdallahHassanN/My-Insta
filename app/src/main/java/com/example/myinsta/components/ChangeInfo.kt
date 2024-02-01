package com.example.myinsta.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myinsta.components.ChangeInfoTextField
import com.example.myinsta.components.MyButton
import com.example.myinsta.ui.theme.BigStone

@Composable
fun ChangeNameInfo(
    pageText:String,
    labelText:String,
    text:String,
    onClick:() -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(BigStone)
    ) {
        Text(
            text = pageText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(20.dp)
        )
        ChangeInfoTextField(label = labelText, query = text)
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            MyButton(text = "Save",onClick = onClick)
        }
    }

}