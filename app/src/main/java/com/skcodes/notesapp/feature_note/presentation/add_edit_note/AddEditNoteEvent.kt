package com.skcodes.notesapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class ChangeColor(val color:Int):AddEditNoteEvent()
    data class EnteredTitle(val title:String):AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState:FocusState):AddEditNoteEvent()
    data class EnteredContent(val content:String):AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState):AddEditNoteEvent()
    data object SaveNote:AddEditNoteEvent()
}
