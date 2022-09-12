package com.example.githubsearch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.domain.model.Repo
import com.example.domain.model.User
import com.example.githubsearch.data.datasource.remote.UserRemoteDataSource
import com.example.githubsearch.database.GithubSearchDatabase
import com.example.githubsearch.database.model.UserEntity
import com.example.githubsearch.dto.asDomainModel
import com.example.githubsearch.util.Constants.Companion.PAGINATION_PREFETCH_DISTANCE_DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Doesn't know how to make abstraction of "PagingData" without adding "androidx.paging" dependency to domain module.
// So, I decided to not create abstraction of this repository.
class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    // Doesn't know how to make abstraction of "Value" data type of "PagingSource<Key, Value>".
    // So, I decided to not create abstraction of this local data source.
    // https://issuetracker.google.com/issues/206697857.
    private val githubSearchDatabase: GithubSearchDatabase
) {
    suspend fun searchUsers(searchQuery: String): Flow<PagingData<User>> {
        return withContext(Dispatchers.IO) {
            val pagingSourceFactory: () -> PagingSource<Int, UserEntity> = {
                githubSearchDatabase.userDao().getUsers()
            }

            @OptIn(ExperimentalPagingApi::class)
            Pager(
                config = PagingConfig(
                    pageSize = PAGINATION_PAGE_SIZE,
                    enablePlaceholders = false,
                    initialLoadSize = PAGINATION_INITIAL_LOAD_SIZE,
                    prefetchDistance = PAGINATION_PREFETCH_DISTANCE
                ),
                initialKey = null,
                remoteMediator = UsersSearchResultRemoteMediator(
                    searchQuery = searchQuery,
                    userRemoteDataSource = userRemoteDataSource,
                    githubSearchDatabase = githubSearchDatabase
                ),
                pagingSourceFactory = pagingSourceFactory
            ).flow.map { pagingData ->
                pagingData.map {
                    it.asDomainModel()
                }
            }
        }
    }

    suspend fun getUserRepositories(username: String): Flow<PagingData<Repo>> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val PAGINATION_PAGE_SIZE = 10
        private const val PAGINATION_INITIAL_LOAD_SIZE = PAGINATION_PAGE_SIZE * 2
        private const val PAGINATION_PREFETCH_DISTANCE = PAGINATION_PREFETCH_DISTANCE_DEFAULT
    }
}