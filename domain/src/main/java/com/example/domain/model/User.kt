package com.example.domain.model

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
)
