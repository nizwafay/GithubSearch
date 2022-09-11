package com.example.githubsearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubsearch.database.dao.UserDao
import com.example.githubsearch.database.dao.UsersSearchResultRemoteKeysDao
import com.example.githubsearch.database.model.UserEntity
import com.example.githubsearch.database.model.UsersSearchResultRemoteKeyEntity

@Database(
    entities = [UserEntity::class, UsersSearchResultRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubSearchDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun usersSearchResultRemoteKeysDao(): UsersSearchResultRemoteKeysDao

    companion object {
        @Volatile
        private var instance: GithubSearchDatabase? = null

        fun getInstance(context: Context): GithubSearchDatabase = instance ?: synchronized(this) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GithubSearchDatabase::class.java,
            "github_search_database.db"
        ).build()
    }
}