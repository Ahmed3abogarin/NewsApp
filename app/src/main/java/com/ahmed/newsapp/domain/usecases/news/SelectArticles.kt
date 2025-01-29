package com.ahmed.newsapp.domain.usecases.news

import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}