package com.ahmed.newsapp.presentation.details

import com.ahmed.newsapp.domain.model.Article

sealed class DetailsEvent {

    data class UpsertDeleteArticle(val article: Article): DetailsEvent()

    data object RemoveSideEffect: DetailsEvent()
}