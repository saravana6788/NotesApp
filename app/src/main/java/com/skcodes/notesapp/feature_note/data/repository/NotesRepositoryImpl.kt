package com.skcodes.notesapp.feature_note.data.repository

import com.skcodes.notesapp.feature_note.data.database.NotesDao
import com.skcodes.notesapp.feature_note.domain.model.Note
import com.skcodes.notesapp.feature_note.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val notesDao: NotesDao):NotesRepository {
    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes()
    }

    override suspend fun getNoteById(id:Int?): Note? {
        return notesDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        notesDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }
}