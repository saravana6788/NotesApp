package com.skcodes.notesapp.feature_note.domain.repository

import com.skcodes.notesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id:Int?):Note?

    suspend fun insertNote(note:Note)

    suspend fun deleteNote(note:Note)
}