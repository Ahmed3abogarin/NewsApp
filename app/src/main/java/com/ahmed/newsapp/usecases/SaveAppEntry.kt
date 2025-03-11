package com.ahmed.newsapp.usecases

import com.ahmed.newsapp.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    // operator ==> We can call method with class name

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}