package com.example.githubsearch.dto

import com.example.domain.model.User
import com.example.githubsearch.api.model.response.GetUserDetailResponse
import com.example.githubsearch.api.model.response.SearchUsersResponse

fun SearchUsersResponse.asListOfUsersUsername(): List<String> = items.map {
    it.login
}

fun GetUserDetailResponse.asDomainModel(): User = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    name = name,
    company = company,
    location = location,
    email = email,
    bio = bio,
    followers = followers,
    following = following
)