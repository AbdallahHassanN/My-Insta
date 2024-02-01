package com.example.myinsta.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Query

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangeInfoTextField(
    label: String,
    query: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val newInfo = remember { mutableStateOf(query) }
    TextField(
        value = newInfo.value,
        onValueChange = { newValue ->
            newInfo.value = newValue
        },
        Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                end = 20.dp,
                start = 20.dp
            )
            .border(
                width = 1.dp,
                color = Color.Gray
            ),
        label = {
            Text(text = label)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
            autoCorrect = true
        ),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black, // Change text color
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White, // Change background color
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            cursorColor = Color.Black, // Change cursor color
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray,
        ),
    )
}
@Preview(showBackground = true)
@Composable
fun DisplayInfoTF(){
    ChangeInfoTextField(label = "ASD", query = "ASD")
}