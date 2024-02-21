package com.example.myinsta.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UsernameOrEmailTextField(
    label:String,
    inputValue:String,
    onQueryChanged: (String) -> Unit,
    isError:Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = inputValue,
        onValueChange =
        {
            newValue ->
            onQueryChanged(newValue)
        },
        Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                end = 20.dp,
                start = 20.dp,
            ),
        label = {
            Text(text = label)
        },
        isError = isError,
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