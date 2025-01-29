package com.ahmed.newsapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source, // This is object, and the ROOM db only can store primitive data type
    val title: String,
    @PrimaryKey val url: String,
    val urlToImage: String
): Parcelable