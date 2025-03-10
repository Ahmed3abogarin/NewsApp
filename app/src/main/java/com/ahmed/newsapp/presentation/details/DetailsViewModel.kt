package com.ahmed.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.newsapp.domain.model.Article
import com.ahmed.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    // we need this one to determine if we should delete or insert the article because the same button/icon will do the job
    var sideEffect by mutableStateOf<String?>(null)
        private set // private set ==> so it can't be accessed from outside the viewmodel (so we can just change it from the viewmodel)


    fun onEvent(event: DetailsEvent) {
            when (event) {
                is DetailsEvent.UpsertDeleteArticle -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val article = newsUseCases.selectArticle(url = event.article.url)
                        if (article == null) {
                            // if it null we need to insert it
                            upsertArticle(event.article)
                        } else {
                            deleteArticle(event.article)
                        }

                    }
                }
                is DetailsEvent.RemoveSideEffect -> {
                    sideEffect = null
                }
            }




    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        // to inform the ui that we add a new article
        sideEffect = "Article saved"

    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        // to inform the ui that we add a new article
        sideEffect = "Article deleted"

    }
}