package com.skcodes.notesapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skcodes.notesapp.feature_note.domain.model.InvalidNoteException
import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.usecase.NotesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    val notesUseCases: NotesUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel() {


    private val _noteTitleTextFieldState = mutableStateOf(
        NoteTextFieldState(
        hint = "Enter the title"
        )
    )
    val noteTitleTextFieldState:State<NoteTextFieldState> = _noteTitleTextFieldState


    private val _noteContentTextFieldState = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter the content"
        )
    )
    val noteContentTextFieldState:State<NoteTextFieldState> = _noteContentTextFieldState

    private val _noteColor = mutableStateOf(Note.colors.random().toArgb())
    val noteColor:State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    var currentNoteId:Int? = null

    init{
            savedStateHandle.get<Int>("noteId")?.let{id ->
                if(id !=-1){
                    viewModelScope.launch {
                        notesUseCases.getNote(id)?.also {
                            currentNoteId = it.id
                            _noteTitleTextFieldState.value = noteTitleTextFieldState.value.copy(
                                text = it.title,
                                isHintVisible = false
                            )
                            _noteContentTextFieldState.value = noteContentTextFieldState.value.copy(
                                text = it.content,
                                isHintVisible = false
                            )
                            _noteColor.value = it.color
                        }
                    }
                }
            }
    }


    fun onEvent(addEditNoteEvent: AddEditNoteEvent){
        when(addEditNoteEvent){
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = addEditNoteEvent.color
            }
            
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContentTextFieldState.value =
                    noteContentTextFieldState.value.copy(
                        isHintVisible = !addEditNoteEvent.focusState.isFocused &&
                        noteContentTextFieldState.value.text.isBlank()
                    )
            }
            
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitleTextFieldState.value =
                    noteTitleTextFieldState.value.copy(
                        isHintVisible = !addEditNoteEvent.focusState.isFocused &&
                                noteTitleTextFieldState.value.text.isBlank()
                    )
            }
            
            is AddEditNoteEvent.EnteredContent -> {
                _noteContentTextFieldState.value = 
                    noteContentTextFieldState.value.copy(
                        text = addEditNoteEvent.content
                    )
            }
            
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitleTextFieldState.value =
                    noteTitleTextFieldState.value.copy(
                        text = addEditNoteEvent.title
                    )
            }
            
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try{
                        notesUseCases.addNote(Note(
                            title = noteTitleTextFieldState.value.text,
                            content =noteContentTextFieldState.value.text,
                            color = noteColor.value,
                            timestamp = System.currentTimeMillis(),
                            id = currentNoteId
                        ))
                        _eventFlow.emit(
                            UiEvent.SaveNote
                        )
                    }catch (exception:InvalidNoteException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(exception.message?:"Could not save the note"))
                    }

                }
            }
        }

    }


    sealed class UiEvent{
        data class ShowSnackBar(val message:String):UiEvent()
        object SaveNote:UiEvent()

    }
}