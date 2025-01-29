package com.ahmed.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.presentation.Dimens.MediumPadding1
import com.ahmed.newsapp.presentation.common.ArticleList
import com.ahmed.newsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(top = MediumPadding1, end = MediumPadding1, start = MediumPadding1)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchNews) })
        Spacer(modifier = Modifier.height(MediumPadding1))
        state.articles?.let { article ->
            val articles = article.collectAsLazyPagingItems()
            ArticleList(article = articles, onClick = { navigateToDetails(it) })
        }

    }

}

