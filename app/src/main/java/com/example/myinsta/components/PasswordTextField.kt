package com.example.myinsta.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myinsta.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(
    label: String,
    inputValue: String,
    onQueryChanged: (String) -> Unit,
    isError: Boolean
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    //var password by remember { mutableStateOf("") }

    TextField(
        value = inputValue,
        onValueChange =
        { newValue ->
            onQueryChanged(newValue)
        },
        Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                end = 20.dp,
                start = 20.dp
            ),
        label = {
            Text(text = label)
        },
        isError = isError,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
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
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Icon(
                    painter = if (passwordVisible)
                        painterResource(id = R.drawable.visibility_24px)
                    else painterResource(id = R.drawable.visibility_off_24px),
                    contentDescription = "",
                )
            }
        },
    )
}