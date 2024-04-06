package com.chslcompany.moviesapp.feature_movies.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val darkModePreference = booleanPreferencesKey("user_preferences")

class UserPreferenceRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val isDarkModeFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e("ERROR_DATA STORE", "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[darkModePreference] ?: false
        }

    suspend fun isDarkMode(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[darkModePreference] = value
        }
    }

}
