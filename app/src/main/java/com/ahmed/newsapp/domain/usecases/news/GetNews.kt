package com.ahmed.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.domain.repository.NewsRepository
import com.ahmed.newsapp.data.remote.NewsApi
import kotlinx.coroutines.flow.Flow

class GetNews (
    private val newsRepository: NewsRepository
){
    operator fun invoke(sources: List<String>): Flow<PagingData<Article>>{
        return newsRepository.getNews(sources = sources)
    }
}