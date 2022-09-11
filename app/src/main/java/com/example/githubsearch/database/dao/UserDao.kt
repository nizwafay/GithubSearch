package com.example.githubsearch.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubsearch.database.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users_table WHERE login=:username")
    fun getUsersByUsername(username: String): PagingSource<Int, UserEntity>

    @Query("DELETE FROM users_table")
    suspend fun clearData()
}