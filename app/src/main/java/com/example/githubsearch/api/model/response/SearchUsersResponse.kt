package com.example.githubsearch.api.model.response

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerializedName("items")
    val items: List<Item> = listOf()
) {
    data class Item(
        @SerializedName("login")
        val login: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("node_id")
        val nodeId: String = "",
        @SerializedName("avatar_url")
        val avatarUrl: String = "",
        @SerializedName("gravatar_id")
        val gravatarId: String = "",
        @SerializedName("url")
        val url: String = "",
        @SerializedName("html_url")
        val htmlUrl: String = "",
        @SerializedName("followers_url")
        val followersUrl: String = "",
        @SerializedName("following_url")
        val followingUrl: String = "",
        @SerializedName("gists_url")
        val gistsUrl: String = "",
        @SerializedName("starred_url")
        val starredUrl: String = "",
        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String = "",
        @SerializedName("organizations_url")
        val organizationsUrl: String = "",
        @SerializedName("repos_url")
        val reposUrl: String = "",
        @SerializedName("events_url")
        val eventsUrl: String = "",
        @SerializedName("received_events_url")
        val receivedEventsUrl: String = "",
        @SerializedName("type")
        val type: String = "",
        @SerializedName("site_admin")
        val siteAdmin: Boolean = false,
        @SerializedName("score")
        val score: Double = 0.0
    )
}