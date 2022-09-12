package com.example.githubsearch.di

import com.example.githubsearch.data.UserRepository
import com.example.githubsearch.data.datasource.remote.UserRemoteDataSource
import com.example.githubsearch.database.GithubSearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSource,
        githubSearchDatabase: GithubSearchDatabase
    ): UserRepository {
        return UserRepository(
            userRemoteDataSource = userRemoteDataSource,
            githubSearchDatabase = githubSearchDatabase
        )
    }
}