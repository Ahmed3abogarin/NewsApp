package com.ahmed.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.ahmed.newsapp.R

data class Page(
    val title: String,
    val description:String,
    @DrawableRes val image : Int,
)

val pages = listOf(
    Page(
        "Stay Informed",
        "Stay updated with breaking news and stories that matter to you",
        R.drawable.second_ill
    ),
    Page(
        "Sharing is Caring",
        "Share the news that matters with your friends and family",
        R.drawable.first_ill
    ),
    Page(
        "Save What Matters",
        "Bookmark the content you love and revisit it anytime, anywhere",
        R.drawable.third_ill
    )
)