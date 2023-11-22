package com.example.handybook.model

data class Comment(
    var book_id: Int,
    val user_id: Int,
    val text: String,
    val reyting: String,
    val id:Int
)