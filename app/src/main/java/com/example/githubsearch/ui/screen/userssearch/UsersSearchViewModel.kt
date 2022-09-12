package com.example.githubsearch.ui.screen.userssearch

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.domain.model.User
import com.example.githubsearch.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UsersSearchViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val usersSearchResult: Flow<PagingData<UiModel>> = searchQuery.flatMapLatest {
        userRepository.searchUsers(it)
    }.map { pagingData ->
        pagingData.map {
            UiModel.UserItem(it)
        }.insertSeparators { before, after ->
            when {
                before == null || after == null -> null
                else -> UiModel.SeparatorItem("Separator: $before-$after")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}

sealed class UiModel {
    class UserItem(val user: User) : UiModel()
    class SeparatorItem(val tag: String) : UiModel()
}