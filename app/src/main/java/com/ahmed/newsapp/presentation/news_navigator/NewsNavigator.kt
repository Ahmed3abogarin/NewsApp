package com.ahmed.newsapp.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahmed.newsapp.R
import com.ahmed.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.ahmed.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.presentation.bookmark.BookMarkScreen
import com.ahmed.newsapp.presentation.bookmark.BookMarkViewModel
import com.ahmed.newsapp.presentation.details.DetailsEvent
import com.ahmed.newsapp.presentation.details.DetailsScreen
import com.ahmed.newsapp.presentation.details.DetailsViewModel
import com.ahmed.newsapp.presentation.home.HomeScreen
import com.ahmed.newsapp.presentation.home.HomeViewModel
import com.ahmed.newsapp.presentation.nvgraph.Route
import com.ahmed.newsapp.presentation.search.SearchScreen
import com.ahmed.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigatorScreen() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
        )
    }

    // we have two navGraph :
    // 1- for onBoarding screen and splash screen
    // 2- news navigator and bottom navigation screens

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = remember(key1 = backstackState){
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }



    // we want to hide the bottom navigation bar when we in the details/bookmark screen
    val isBottomBarVisible = remember(key1 = backstackState){
        backstackState?.destination?.route == Route.HomeScreen.route ||
        backstackState?.destination?.route == Route.SearchScreen.route ||
        backstackState?.destination?.route == Route.BookmarkScreen.route
    }



    // Scaffold == one of the material theme that dived your screen into parts like top app bar, bottom navigation bar and navigation reel

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible){
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClicked = { index ->
                        when (index) {
                            0 -> navigateToTan(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTan(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTan(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }

                    }
                )
            }

        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()


                // in the home screen there are two options to navigate
                // 1- navigate to the search screen by click the search bar
                // 2- navigate to details screen
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTan(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }


            // For the search screen
            composable(Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            article = it
                        )
                    })

            }

            // for details screen
            composable(route = Route.DetailsScreen.route){
                val viewModel: DetailsViewModel = hiltViewModel()
                //show view model using side effect
                if (viewModel.sideEffect != null){
                    Toast.makeText(LocalContext.current,viewModel.sideEffect,Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }

                // retrieve the article subject
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")?.let { article ->
                    DetailsScreen(article = article, event = viewModel::onEvent, navigateUp = {navController.navigateUp()})
                }


            }

            // for bookmark screen
            composable(route = Route.BookmarkScreen.route){
                val viewModel: BookMarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookMarkScreen(state = state, navigateToDetails = {article ->
                    navigateToDetails(navController = navController, article= article )
                })
            }
        }
    }

}

fun navigateToTan(navController: NavController, route: String) {
    navController.navigate(route) {
        // every time we navigate to tab we wanna pop the backstack until we reach the home screen
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop =
                true // if you clicked multiple time on home screen icon that won't create a new instance of home screen each time
        }


    }
}


// create helper function help us to navigate to details screen
private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )

}