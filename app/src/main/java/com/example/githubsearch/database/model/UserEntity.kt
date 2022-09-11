package com.example.githubsearch.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "users_table",
    primaryKeys = ["id", "login"]
)
data class UserEntity(
    val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    val name: String?,
    val company: String?,
    val location: String?,
    val email: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
)