package com.ahmed.newsapp.presentation.bookmark

import com.ahmed.newsapp.domain.model.Article

data class BookMarkState(
    val articles: List<Article> = emptyList()
)