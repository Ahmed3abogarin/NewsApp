package com.ahmed.newsapp.domain.usecases.news

import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.domain.repository.NewsRepository
import com.ahmed.newsapp.data.local.NewsDao

class DeleteArticle(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article)
    }
}