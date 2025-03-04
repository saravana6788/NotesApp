package com.skcodes.notesapp.feature_note.presentation.notes

import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.model.NoteOrder

sealed class NotesEvent {
    data object RestoreNote:NotesEvent()
    data class InsertNote(val note: Note): NotesEvent()
    data class DeleteNote(val note:Note):NotesEvent()
    data class OrderNote(val noteOrder: NoteOrder):NotesEvent()
    data object ToggleOrderSection:NotesEvent()
}