package com.skcodes.notesapp.feature_note.domain.usecase

import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.repository.NotesRepository

class GetNoteUseCase(val repository: NotesRepository) {

    suspend operator fun invoke(id:Int):Note?{
        return repository.getNoteById(id)

    }
}