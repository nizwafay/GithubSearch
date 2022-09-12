package com.example.githubsearch.api.datasourceimpls

import com.example.domain.datasource.remote.UserApi
import com.example.domain.model.Repo
import com.example.domain.model.User
import com.example.githubsearch.api.UserRetrofit
import com.example.githubsearch.dto.asDomainModel
import com.example.githubsearch.dto.asListOfUsersUsername
import javax.inject.Inject

class UserApiImpl @Inject constructor(private val userRetrofit: UserRetrofit) : UserApi {
    override suspend fun searchUsers(
        searchQuery: String,
        pageNumber: Int,
        pageSize: Int
    ): List<String> {
        return userRetrofit.searchForUsers(
            searchQuery = searchQuery,
            pageNumber = pageNumber,
            pageSize = pageSize
        ).asListOfUsersUsername()
    }

    override suspend fun getUserDetail(username: String): User {
        return userRetrofit.getUserDetail(username = username).asDomainModel()
    }

    override suspend fun getUserRepos(username: String): List<Repo> {
        TODO("Not yet implemented")
    }
}