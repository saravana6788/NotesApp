package com.skcodes.notesapp.feature_note.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skcodes.notesapp.feature_note.presentation.notes.components.NoteItem
import com.skcodes.notesapp.feature_note.presentation.notes.components.OrderSection
import com.skcodes.notesapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel:NotesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember{SnackbarHostState()}

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = Screen.AddEditNoteScreen.route)},
                modifier = Modifier.background(Color.Transparent)
                    .clip(shape = CircleShape)
                    .shadow(shape = CircleShape,
                        elevation = 15.dp)


            ){
                Icon(
                    imageVector =  Icons.Default.Add,
                    contentDescription = "Add Note"
                )
            }
        },
        snackbarHost = {SnackbarHost(snackBarHostState)}

    ) { innerPadding ->

        Column(modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)){
           Row(
               Modifier.fillMaxWidth(), Arrangement.SpaceBetween,
           ) {

               Text(
                   text = "All Notes",
                   style = MaterialTheme.typography.headlineMedium
               )

               IconButton(
                   onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
               ) {
                   Icon(
                       imageVector = Icons.Default.Sort,
                       contentDescription = "Sort",

                       )
               }

           }

            Spacer(modifier = Modifier.height(16.dp))

               AnimatedVisibility(
                   visible = viewModel.state.value.isOrderSectionVisible,
                   enter = fadeIn() + expandVertically(),
                   exit = fadeOut() + shrinkVertically()
               ) {
                   OrderSection(
                       modifier = Modifier.fillMaxWidth()
                           .padding(16.dp),
                       noteOrder = state.noteOrder,
                       onNoteOrderChange = {
                           viewModel.onEvent(NotesEvent.OrderNote(it))
                       }
                   )
               }

               Spacer(modifier = Modifier.height(16.dp))

               LazyColumn(modifier = Modifier.fillMaxSize()
                   ) {
                    val notes = state.notes
                  items(notes) {
                      NoteItem(
                          modifier = Modifier.fillMaxWidth()
                              .clickable {
                                  navController.navigate(
                                      Screen.AddEditNoteScreen.route+"?noteId=${it.id}&noteColor=${it.color}"
                                  )
                              },
                          note = it,
                          onDeleteClick = {viewModel.onEvent(NotesEvent.DeleteNote(it))
                          scope.launch {
                              val result = snackBarHostState.showSnackbar(
                                  message = "Note Deleted Successfully",
                                  actionLabel = "Undo",
                                  duration = SnackbarDuration.Short
                              )

                              if(result== SnackbarResult.ActionPerformed){
                                  viewModel.onEvent(NotesEvent.RestoreNote)
                              }
                          }}
                      )

                      Spacer(modifier = Modifier.height(16.dp))
                  }

               }

           }
        }
    }

