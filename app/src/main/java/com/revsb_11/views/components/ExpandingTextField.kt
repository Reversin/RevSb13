package com.revsb_11.views.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun ExpandingTextField(modifier: Modifier, text: String, onTextChanged: (String) -> Unit) {
    // Состояние для хранения текста в TextField
    val textState = remember { mutableStateOf(TextFieldValue(text)) }

    // Состояние для управления прокруткой
    val scrollState = rememberScrollState()

    Box(modifier = modifier.verticalScroll(scrollState)) {
        OutlinedTextField(
            modifier = modifier,
            value = textState.value,
            onValueChange = { changedText ->
                textState.value = changedText
                onTextChanged(changedText.toString())
            },
        )
    }
}
