package com.example.cataloguerpoe.models

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profileCompleted: Int = 0
)
