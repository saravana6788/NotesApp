package com.skcodes.notesapp.feature_note.domain.model

sealed class OrderType {
    data object Ascending: OrderType()
    data object Descending:OrderType()

}