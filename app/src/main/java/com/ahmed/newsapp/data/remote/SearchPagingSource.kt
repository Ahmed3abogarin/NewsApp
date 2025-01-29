package com.ahmed.newsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahmed.newsapp.domain.model.Article

class SearchPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery: String,
    private val sources : String
): PagingSource<Int,Article> (){

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1

        return try {
            val newsResponse =
                newsApi.newsSearch(query = searchQuery,page = page, sources = sources)

            totalNewsCount += newsResponse.articles.size
            val articles =
                newsResponse.articles.distinctBy { it.title } // to remove any duplicate articles because sometimes the api returns duplicate articatles
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1 // if it's null the paging will stop loading new result or make new API calls that's why we need the "totalNewsCount"
                ,prevKey = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition -> // anchorPosition ==> latest access page in the list
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}