package com.skcodes.notesapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlin.math.sin

@Composable
fun TransparentHintTextField(
    modifier:Modifier = Modifier,
    hint:String,
    isHintVisible:Boolean,
    text:String,
    singleLine:Boolean,
    textStyle: TextStyle,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) ->Unit
){

    Box(modifier = Modifier){
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }

        )

        if(isHintVisible){
            Text(text = hint, style = textStyle,
                color = Color.Black)
        }
    }
}