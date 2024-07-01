package com.example.todolistwithcompose.presentor.myUi


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todolistwithcompose.domain.TabItem
import com.example.todolistwithcompose.domain.Task
import com.example.todolistwithcompose.navigation.AppNavGraph
import com.example.todolistwithcompose.navigation.rememberNavigationState
import com.example.todolistwithcompose.presentor.state.TestNavigationTabState
import com.example.todolistwithcompose.presentor.viewModel.TestTabNavigationViewModel
import com.example.todolistwithcompose.presentor.viewModel.ViewModelFactory
import kotlinx.coroutines.launch
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AAA() {
    val viewModel = viewModel<TestTabNavigationViewModel>(factory =  ViewModelFactory(LocalContext.current))
    val stateViewModel by viewModel.state.collectAsState()
    val tabs = TabItem.tabs
    val state = remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {tabs.size }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
         state.intValue = page
            viewModel.getTasks(page)
            Log.d("Page change", "Page changed to $page")
        }
    }

    Column {
       TabRow (selectedTabIndex = tabs.size) {
            tabs.forEach {  tab ->
                    Tab(
                        selected = tab.tabId == state.intValue,
                        onClick = {
                                state.intValue = tab.tabId
                            scope.launch { pagerState.animateScrollToPage(tab.tabId)
                            }
                                  },
                        text = { Text(text = tab.title) },
                        icon = { Icon(
                            imageVector = if (tab.tabId == state.intValue) tab.selectedIcon else tab.unselectedIcon,
                            contentDescription = ""
                        ) }
                    )
            }
        }
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val currentState = stateViewModel
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if(currentState is TestNavigationTabState.Result){
                    Foo(tasks = currentState.task, onDismissListener = {}, onTaskListener = {})
                }
            }
        }
    }
}


