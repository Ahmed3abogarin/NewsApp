package com.ahmed.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.ahmed.newsapp.R
import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.presentation.Dimens.MediumPadding1
import com.ahmed.newsapp.presentation.common.ArticleList
import com.ahmed.newsapp.presentation.nvgraph.Route

@Composable
fun BookMarkScreen(
    state: BookMarkState,
    navigateToDetails: (Article) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)

    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        ArticleList(articles = state.articles, onClick = {navigateToDetails(it)})
        // to preview that let's go to navGraph


    }
}