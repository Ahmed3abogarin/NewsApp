package com.ahmed.newsapp.domain.usecases.app_entry

import com.ahmed.newsapp.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    // operator ==> We can call method with class name

    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}