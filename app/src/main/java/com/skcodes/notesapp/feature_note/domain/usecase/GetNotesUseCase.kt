package com.skcodes.notesapp.feature_note.domain.usecase

import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.model.NoteOrder
import com.skcodes.notesapp.feature_note.domain.model.OrderType
import com.skcodes.notesapp.feature_note.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(
    private val notesRepository: NotesRepository
) {

    operator fun invoke(
            noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ):Flow<List<Note>>{
        return notesRepository.getNotes()
            .map { notes ->
                when(noteOrder.orderType){
                    is OrderType.Ascending ->{
                        when(noteOrder){
                            is NoteOrder.Color -> notes.sortedBy { it.color }
                            is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        }
                    }
                    is OrderType.Descending ->{
                        when(noteOrder){
                            is NoteOrder.Color -> notes.sortedByDescending { it.color }
                            is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        }
                    }
                }

            }

    }
}