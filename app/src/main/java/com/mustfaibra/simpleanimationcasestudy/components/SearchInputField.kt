package com.mustfaibra.simpleanimationcasestudy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colors.surface,
    onValueChange: (string: String) -> Unit,
    onFocusChange: (focused: Boolean) -> Unit,
    onKeyboardActionClicked: KeyboardActionScope.() -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .focusRequester(focusRequester)
            .onFocusChanged {
                onFocusChange(it.isFocused)
            }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        decorationBox = { container ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = textStyle,
                    color = textColor.copy(alpha = 0.5f),
                    maxLines = 1,
                )
            }
            container()
        },
        singleLine = true,
        textStyle = textStyle.copy(color = textColor),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onAny = {
                focusRequester.freeFocus()
                /** It doesn't has the focus now, hide the input keyboard */
                inputService?.hideSoftwareKeyboard()
                onKeyboardActionClicked()
            }
        ),
        cursorBrush = SolidColor(value = textColor),
    )
}