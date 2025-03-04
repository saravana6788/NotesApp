package com.skcodes.notesapp.di

import android.app.Application
import androidx.room.Room
import com.skcodes.notesapp.feature_note.data.database.NotesDatabase
import com.skcodes.notesapp.feature_note.data.repository.NotesRepositoryImpl
import com.skcodes.notesapp.feature_note.domain.repository.NotesRepository
import com.skcodes.notesapp.feature_note.domain.usecase.AddNoteUseCase
import com.skcodes.notesapp.feature_note.domain.usecase.DeleteNoteUseCase
import com.skcodes.notesapp.feature_note.domain.usecase.GetNoteUseCase
import com.skcodes.notesapp.feature_note.domain.usecase.GetNotesUseCase
import com.skcodes.notesapp.feature_note.domain.usecase.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesNotesDatabase(app:Application):NotesDatabase{
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()

    }

    @Provides
    @Singleton
    fun providesNotesRepository(db:NotesDatabase):NotesRepository{
        return NotesRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun providesNotesUseCase(notesRepository: NotesRepository):NotesUseCases{
        return NotesUseCases(
            getNotes = GetNotesUseCase(notesRepository),
            deleteNote = DeleteNoteUseCase(notesRepository),
            addNote = AddNoteUseCase(notesRepository),
            getNote = GetNoteUseCase(notesRepository)
        )
    }
}