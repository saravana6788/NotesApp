package com.skcodes.notesapp.feature_note.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skcodes.notesapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase: RoomDatabase() {
    abstract val notesDao: NotesDao


    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}

