package com.example.githubsearch.api

import com.example.githubsearch.api.model.response.GetUserDetailResponse
import com.example.githubsearch.api.model.response.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val IN_QUALIFIER = "in:name"

interface UserRetrofit {
    @GET("search/users")
    suspend fun searchForUsers(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int,
        @Query("per_page")
        pageSize: Int,
        @Query("sort")
        sortBy: String = "followers"
    ): SearchUsersResponse

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username")
        username: String
    ): GetUserDetailResponse
}