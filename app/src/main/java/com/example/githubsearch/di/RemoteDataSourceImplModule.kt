package com.example.githubsearch.di

import com.example.domain.datasource.remote.UserApi
import com.example.githubsearch.api.UserRetrofit
import com.example.githubsearch.api.datasourceimpls.UserApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceImplModule {
    @Singleton
    @Provides
    fun provideUserApi(userRetrofit: UserRetrofit): UserApi {
        return UserApiImpl(userRetrofit = userRetrofit)
    }
}