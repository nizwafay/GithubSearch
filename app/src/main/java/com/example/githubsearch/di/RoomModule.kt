package com.example.githubsearch.di

import android.content.Context
import com.example.githubsearch.database.GithubSearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideGithubSearchDatabase(@ApplicationContext context: Context): GithubSearchDatabase {
        return GithubSearchDatabase.getInstance(context)
    }
}