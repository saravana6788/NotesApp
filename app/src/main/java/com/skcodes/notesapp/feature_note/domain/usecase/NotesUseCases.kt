package com.skcodes.notesapp.feature_note.domain.usecase

data class NotesUseCases(
    val getNotes:GetNotesUseCase,
    val deleteNote:DeleteNoteUseCase,
    val addNote:AddNoteUseCase,
    val getNote:GetNoteUseCase
)