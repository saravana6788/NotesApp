package com.skcodes.notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skcodes.notesapp.ui.theme.BabyBlue
import com.skcodes.notesapp.ui.theme.RedOrange
import com.skcodes.notesapp.ui.theme.RedPink
import com.skcodes.notesapp.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey
    val id:Int? = null,
    val title:String,
    val content:String,
    val color:Int,
    val timestamp:Long
){
    companion object {
        val colors = listOf(RedOrange,RedPink,Violet, BabyBlue)
    }

}


class InvalidNoteException(errorMessage:String):Exception(errorMessage)
