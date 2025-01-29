package com.ahmed.newsapp.domain.usecases.news

import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.domain.repository.NewsRepository
import com.ahmed.newsapp.data.local.NewsDao

class SelectArticle(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(url: String): Article? {
        return newsRepository.selectedArticle(url = url)
    }
}