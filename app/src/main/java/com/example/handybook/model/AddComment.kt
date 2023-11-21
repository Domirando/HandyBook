package uz.itteacher.mybook.moedel

data class AddComment(
    val book_id: Int,
    var id: Int = 0,
    val reyting: Double,
    val text: String,
    val user_id: Int
)