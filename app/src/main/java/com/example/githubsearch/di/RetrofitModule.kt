package com.example.githubsearch.di

import com.example.githubsearch.api.RetrofitInstance
import com.example.githubsearch.api.UserRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return RetrofitInstance.createInstance()
    }

    @Singleton
    @Provides
    fun provideUserRetrofit(retrofitInstance: Retrofit): UserRetrofit {
        return retrofitInstance.create(UserRetrofit::class.java)
    }
}