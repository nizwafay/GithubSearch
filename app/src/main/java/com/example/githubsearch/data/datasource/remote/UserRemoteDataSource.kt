package com.example.githubsearch.data.datasource.remote

import com.example.domain.datasource.remote.UserApi
import com.example.domain.model.User
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val userApi: UserApi) {
    suspend fun searchUsers(searchQuery: String, pageNumber: Int, pageSize: Int): List<User> {
        val usersUsername: List<String> = userApi.searchUsers(
            searchQuery = searchQuery,
            pageNumber = pageNumber,
            pageSize = pageSize
        )

        return usersUsername.map {
            userApi.getUserDetail(username = it)
        }
    }
}