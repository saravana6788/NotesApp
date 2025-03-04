package com.skcodes.notesapp.feature_note.presentation.notes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.model.NoteOrder
import com.skcodes.notesapp.feature_note.domain.model.OrderType
import com.skcodes.notesapp.feature_note.domain.usecase.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    val notesUseCases: NotesUseCases):ViewModel() {

        private val _state:MutableState<NotesState> = mutableStateOf(NotesState())
        val state: State<NotesState> = _state

        private var recentlyDeletedNote: Note? = null

    private var getNotesJob:Job? = null

    init {
        getNotes(noteOrder =  NoteOrder.Date(OrderType.Descending))
    }

        fun onEvent(notesEvent: NotesEvent){
            when(notesEvent){
                is NotesEvent.DeleteNote -> {
                    viewModelScope.launch {
                        notesUseCases.deleteNote(notesEvent.note)
                        recentlyDeletedNote = notesEvent.note
                    }
                }

                is NotesEvent.InsertNote -> {
                    viewModelScope.launch {
                        notesUseCases.addNote(notesEvent.note)
                    }
                }

                is NotesEvent.OrderNote -> {
                    if((_state.value.noteOrder::class == notesEvent.noteOrder::class) &&
                        (_state.value.noteOrder.orderType == notesEvent.noteOrder.orderType)){
                            return
                        }
                    getNotes(notesEvent.noteOrder)
                }

                NotesEvent.RestoreNote -> {
                    viewModelScope.launch {
                        notesUseCases.addNote(
                            recentlyDeletedNote?:return@launch
                        )
                        recentlyDeletedNote = null
                    }

                }
                NotesEvent.ToggleOrderSection -> {
                    _state.value = _state.value.copy(
                        isOrderSectionVisible = !state.value.isOrderSectionVisible
                    )

                }
            }
        }


    private fun getNotes(noteOrder: NoteOrder){
       getNotesJob?.cancel()
       getNotesJob = notesUseCases.getNotes(noteOrder).onEach { notes ->
           _state.value = state.value.copy(
               notes = notes,
               noteOrder = noteOrder
           )
       }.launchIn(viewModelScope)
    }



}