package com.skcodes.notesapp.feature_note.presentation.notes

import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.model.NoteOrder
import com.skcodes.notesapp.feature_note.domain.model.OrderType

data class NotesState(
    val notes:List<Note> =  emptyList(),
    val isOrderSectionVisible:Boolean = false,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
)