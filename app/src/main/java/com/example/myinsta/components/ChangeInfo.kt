package com.example.myinsta.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myinsta.ui.theme.BigStone
import com.example.myinsta.ui.theme.QuickSandTypography

@Composable
fun ChangeNameInfo(
    pageText: String,
    labelText: String,
    text: String,
    onClick: () -> Unit,
    onTextChanged: (String) -> Unit, // New lambda parameter for text change callback
) {
    Column(
        Modifier
            .wrapContentSize()
            .background(BigStone)
    ) {
        Text(
            text = pageText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(20.dp),
            style = QuickSandTypography.headlineMedium
        )
        ChangeInfoTextField(label = labelText, query = text, onTextChanged = onTextChanged)

        MyButton(text = "Save", onClick = onClick)
    }
}