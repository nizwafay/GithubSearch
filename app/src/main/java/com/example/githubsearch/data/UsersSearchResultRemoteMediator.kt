package com.example.githubsearch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.domain.datasource.remote.UserRemoteDataSource
import com.example.domain.model.User
import com.example.githubsearch.database.GithubSearchDatabase
import com.example.githubsearch.database.model.UserEntity
import com.example.githubsearch.database.model.UsersSearchResultRemoteKeyEntity
import com.example.githubsearch.dto.asEntityModel
import com.example.githubsearch.util.Constants.Companion.PAGINATION_STARTING_PAGE_DEFAULT
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UsersSearchResultRemoteMediator(
    private val searchQuery: String,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val githubSearchDatabase: GithubSearchDatabase
) : RemoteMediator<Int, UserEntity>() {
    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state = state)
                remoteKeys?.nextKey?.minus(1) ?: PAGINATION_STARTING_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state = state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state = state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val searchUsersResult: List<User> = userRemoteDataSource.searchUsers(
                searchQuery = searchQuery,
                pageNumber = page,
                pageSize = state.config.pageSize
            )

            val endOfPaginationReached = searchUsersResult.isEmpty()
            with(githubSearchDatabase) {
                withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        usersSearchResultRemoteKeysDao().clearData()
                        userDao().clearData()
                    }
                    val prevKey = if (page == PAGINATION_STARTING_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = searchUsersResult.map {
                        UsersSearchResultRemoteKeyEntity(
                            userId = it.id,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    usersSearchResultRemoteKeysDao().insertRemoteKeys(remoteKeys = keys)
                    userDao().insertUsers(searchUsersResult.map {
                        it.asEntityModel()
                    })
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): UsersSearchResultRemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { userEntity ->
                // Get the remote keys of the last item retrieved
                githubSearchDatabase.usersSearchResultRemoteKeysDao()
                    .getUserRemoteKey(userId = userEntity.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): UsersSearchResultRemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { userEntity ->
                // Get the remote keys of the first items retrieved
                githubSearchDatabase.usersSearchResultRemoteKeysDao()
                    .getUserRemoteKey(userId = userEntity.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UserEntity>
    ): UsersSearchResultRemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { userId ->
                githubSearchDatabase.usersSearchResultRemoteKeysDao()
                    .getUserRemoteKey(userId = userId)
            }
        }
    }

    companion object {
        private const val PAGINATION_STARTING_INDEX = PAGINATION_STARTING_PAGE_DEFAULT
    }
}