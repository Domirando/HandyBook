package com.example.handybook.model

data class AddComment(
    val book_id: Int,
    var user_id: Int = 0,
    val text: String,
    val reyting: Int,
    )