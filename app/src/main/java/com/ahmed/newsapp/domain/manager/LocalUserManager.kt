package com.ahmed.newsapp.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    // save the app entry
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>
}