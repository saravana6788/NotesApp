package com.skcodes.notesapp.feature_note.domain.usecase

import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.repository.NotesRepository

class DeleteNoteUseCase(private val repository: NotesRepository) {

    suspend operator fun invoke(note: Note){
        return repository.deleteNote(note)
    }
}