package com.example.domain.datasource.remote

import com.example.domain.model.User

class UserRemoteDataSource(private val userApi: UserApi) {
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