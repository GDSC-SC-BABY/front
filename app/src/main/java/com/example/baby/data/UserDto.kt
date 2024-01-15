package com.example.baby.data

import java.util.*


data class UserDto(
    val id: Long,
    val nickname: String,
    val residence: String,
    val email: String,
    val mobile: String,
    val bId: Long?
)