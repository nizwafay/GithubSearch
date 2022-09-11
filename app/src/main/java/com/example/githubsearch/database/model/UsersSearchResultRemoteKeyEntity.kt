package com.example.githubsearch.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_search_result_remote_keys_table")
data class UsersSearchResultRemoteKeyEntity(
    @PrimaryKey
    val userId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
