package com.example.githubsearch.ui.adapter

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.components.R
import com.example.components.common.ItemHorizontalSeparator
import com.example.components.itempreviewfull.ItemPreviewUserSnippet
import com.example.domain.model.User
import com.example.githubsearch.ui.screen.userssearch.UiModel

class UsersSearchResultAdapter :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(UsersSearchResultComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_USER) {
            ItemPreviewUserSnippetViewHolder(ItemPreviewUserSnippet(context = parent.context).apply {
                layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            })
        } else {
            val item = ItemHorizontalSeparator(context = parent.context).apply {
                layoutParams = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            }
            ItemSeparatorViewHolder(item.apply {
                marginHorizontal = R.dimen.margin_24
                heightDimenRes = R.dimen.separator_size_default
                colorRes = R.color.separator_color
            })
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item is UiModel.UserItem) {
            (holder as ItemPreviewUserSnippetViewHolder).bind(user = item.user)
        } else {
            (holder as ItemSeparatorViewHolder)
        }
    }

    inner class ItemPreviewUserSnippetViewHolder(private val itemPreviewUserSnippet: ItemPreviewUserSnippet) :
        RecyclerView.ViewHolder(itemPreviewUserSnippet) {
        fun bind(user: User) {
            with(user) {
                itemPreviewUserSnippet.apply {
                    photoUrl = avatarUrl
                    title = name
                    subTitle = login
                    description = company
                    footer1Text = location
                    footer2Text = email
                }
            }
        }
    }

    inner class ItemSeparatorViewHolder(itemHorizontalSeparator: ItemHorizontalSeparator) :
        RecyclerView.ViewHolder(itemHorizontalSeparator)

    object UsersSearchResultComparator : DiffUtil.ItemCallback<UiModel>() {
        override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
            val isSameUserItem =
                oldItem is UiModel.UserItem && newItem is UiModel.UserItem && oldItem.user.id == newItem.user.id

            val isSameSeparatorItem =
                oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem && oldItem.tag == newItem.tag

            return isSameUserItem || isSameSeparatorItem
        }

        override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is UiModel.UserItem) TYPE_USER else TYPE_SEPARATOR
    }

    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_SEPARATOR = 1
    }
}