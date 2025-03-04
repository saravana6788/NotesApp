package com.skcodes.notesapp.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor:Int,
    viewModel: AddEditViewModel = hiltViewModel()
) {

    val snackBarHostState = remember { SnackbarHostState() }

    val noteTitle = viewModel.noteTitleTextFieldState.value
    val noteContent = viewModel.noteContentTextFieldState.value
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(
                if (noteColor != -1)
                    noteColor
                else
                    viewModel.noteColor.value
            )
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collectLatest { uiEvent ->
                when(uiEvent){
                    AddEditViewModel.UiEvent.SaveNote -> navController.navigateUp()
                    is AddEditViewModel.UiEvent.ShowSnackBar -> {
                        snackBarHostState.showSnackbar(
                            message = uiEvent.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {viewModel.onEvent(AddEditNoteEvent.SaveNote)},
                modifier = Modifier.background(color = Color.Transparent)
                    .clip(shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Note"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Note.colors.forEach {
                    val colorInt = it.toArgb()
                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(shape = CircleShape)
                        .background(it)
                        .border(
                            width = 3.dp,
                            color = if (viewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                noteBackgroundAnimatable.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )

                            }
                            viewModel.onEvent(
                                addEditNoteEvent = AddEditNoteEvent.ChangeColor(
                                    colorInt
                                )
                            )
                        }


                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                hint = noteTitle.hint,
                text = noteTitle.text,
                isHintVisible = noteTitle.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.titleLarge,
                onValueChange = {viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))},
                onFocusChange = { viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))},
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                hint = noteContent.hint,
                text = noteContent.text,
                isHintVisible = noteContent.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.titleMedium,
                onValueChange = {viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))},
                onFocusChange = { viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))},
                modifier = Modifier.fillMaxHeight()
            )

        }


    }
}

