package com.example.githubsearch.ui.screen.userssearch

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.components.common.PagingLoadStateAdapter
import com.example.githubsearch.R
import com.example.githubsearch.databinding.FragmentUsersSearchBinding
import com.example.githubsearch.ui.adapter.UsersSearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersSearchFragment : Fragment(R.layout.fragment_users_search) {
    private var fragmentUsersSearchBinding: FragmentUsersSearchBinding? = null

    private val viewModel: UsersSearchViewModel by viewModels()

    private val usersSearchResultAdapter by lazy { UsersSearchResultAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentUsersSearchBinding.bind(view)
        fragmentUsersSearchBinding = binding
        binding.run {
            setupUsersSearchResultRv()
        }

        observeVm(binding)
    }

    override fun onDestroy() {
        fragmentUsersSearchBinding = null
        super.onDestroy()
    }

    private fun observeVm(binding: FragmentUsersSearchBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usersSearchResult.collectLatest {
                usersSearchResultAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            usersSearchResultAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .collect {
                    binding.progressBar.isVisible = it.refresh is LoadState.Loading
                    if (it.refresh is LoadState.NotLoading) {
                        binding.rvUsersSearchResult.scrollToPosition(0)
                    }
                }
        }
    }

    private fun FragmentUsersSearchBinding.setupUsersSearchResultRv() {
        with(usersSearchResultAdapter) {
            rvUsersSearchResult.adapter = withLoadStateHeaderAndFooter(
                PagingLoadStateAdapter(this),
                PagingLoadStateAdapter(this)
            )
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.updateSearchQuery(it)
                }
                return true
            }
        })
    }
}