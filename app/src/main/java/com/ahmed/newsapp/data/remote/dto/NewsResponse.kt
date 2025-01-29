package com.ahmed.newsapp.data.remote.dto

import com.ahmed.newsapp.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)