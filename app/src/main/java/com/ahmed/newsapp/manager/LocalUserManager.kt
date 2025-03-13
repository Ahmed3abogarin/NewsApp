package com.ahmed.newsapp.manager

import kotlinx.coroutines.flow.Flow


interface LocalUserManager {

    // save the app entry
    suspend fun saveAppEntry()

    fun readAppEntry(): Flow<Boolean>
}