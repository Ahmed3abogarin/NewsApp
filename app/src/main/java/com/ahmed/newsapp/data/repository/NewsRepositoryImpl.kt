package com.ahmed.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.domain.repository.NewsRepository
import com.ahmed.newsapp.data.local.NewsDao
import com.ahmed.newsapp.data.remote.NewsApi
import com.ahmed.newsapp.data.remote.NewsPagingSource
import com.ahmed.newsapp.data.remote.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(10) // tell the pager in each request we expect 10 articles as a response
        , pagingSourceFactory = {
            NewsPagingSource(
                newsApi = newsApi,
                source = sources.joinToString(separator = ",")
            )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(10) // tell the pager in each request we expect 10 articles as a response
            , pagingSourceFactory = {
                SearchPagingSource(
                    searchQuery = searchQuery,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
    }

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles() // to show the lasted in the top
    }

    override suspend fun selectedArticle(url: String): Article? {
        return newsDao.getArticle(url)
    }

    // next: We need to implement use case for this

}