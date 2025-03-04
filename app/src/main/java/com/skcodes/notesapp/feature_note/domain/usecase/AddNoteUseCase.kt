package com.skcodes.notesapp.feature_note.domain.usecase

import com.skcodes.notesapp.feature_note.domain.model.InvalidNoteException
import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.repository.NotesRepository

class AddNoteUseCase(private val repository: NotesRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Note Title cannot be empty!")
        }

        if(note.content.isBlank()){
            throw InvalidNoteException("Note Content cannot be empty")
        }

        return repository.insertNote(note = note)
    }
}