package com.example.todolistwithcompose.presentor.addAndUpdateTabItem

import com.example.todolistwithcompose.domain.TabItem

sealed class AddAndUpdateTabState {
    data object Loading : AddAndUpdateTabState()
    data class Result(
        val tabItem: TabItem,
        val errorMessage: String = "",
        var isProblemWithTasks: Boolean = false
    ) : AddAndUpdateTabState()
}