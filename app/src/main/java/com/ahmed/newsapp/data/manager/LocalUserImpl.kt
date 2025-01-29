package com.ahmed.newsapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ahmed.newsapp.domain.manager.LocalUserManager
import com.ahmed.newsapp.utils.Constants
import com.ahmed.newsapp.utils.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserImpl(
    private val context:Context
): LocalUserManager {
    override suspend fun saveAppEntry() {
        context.datastore.edit { settings ->
            settings[PreferencesKeys.APP_KEY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {

        // This will return all the data so we need to map it
        return context.datastore.data.map { preferences->
            preferences[PreferencesKeys.APP_KEY] ?: false

            // next we need useCase: go to domain layer
        }
    }
}

// Get instance of data store
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTINGS)

// now we can simply access this data store with our context, But to save key value inside our data
// store preference we also need thing called (Preference keys)
private object PreferencesKeys{
    val APP_KEY = booleanPreferencesKey(Constants.APP_ENTRY)
}