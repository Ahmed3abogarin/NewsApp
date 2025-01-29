package com.ahmed.newsapp.presentation.nvgraph

sealed class Route(
    val route : String
) {
//    data object OnBoardingScreen: Route("onBoardingScreen")
//    data object HomeScreen: Route("homeScreen")
//    data object SearchScreen: Route("searchScreen")
//    data object BookMarkScreen: Route("bookMarkScreen")
//    data object DetailsScreen: Route("detailsScreen")
//    data object NewsNavigatorScreen: Route("newsNavigator")
//
//    data object AppStartNavigation: Route("appStartNavigation")
//    data object NewsNavigation: Route("newsNavigation")

    object OnBoardingScreen : Route(route = "onBoardingScreen")

    object HomeScreen : Route(route = "homeScreen")

    object SearchScreen : Route(route = "searchScreen")

    object BookmarkScreen : Route(route = "bookMarkScreen")

    object DetailsScreen : Route(route = "detailsScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")

    object NewsNavigation : Route(route = "newsNavigation")

    object NewsNavigatorScreen : Route(route = "newsNavigator")
}