package com.example.domain.datasource.remote

import com.example.domain.model.Repo
import com.example.domain.model.User

interface UserApi {
    suspend fun searchUsers(searchQuery: String, pageNumber: Int, pageSize: Int): List<String>
    suspend fun getUserDetail(username: String): User
    suspend fun getUserRepos(username: String): List<Repo>
}