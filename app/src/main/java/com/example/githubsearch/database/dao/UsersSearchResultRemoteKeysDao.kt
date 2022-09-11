package com.example.githubsearch.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubsearch.database.model.UsersSearchResultRemoteKeyEntity

@Dao
interface UsersSearchResultRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<UsersSearchResultRemoteKeyEntity>)

    @Query("SELECT * FROM users_search_result_remote_keys_table WHERE userId=:userId")
    suspend fun getUserRemoteKey(userId: Int): UsersSearchResultRemoteKeyEntity?

    @Query("DELETE FROM users_search_result_remote_keys_table")
    suspend fun clearData()
}