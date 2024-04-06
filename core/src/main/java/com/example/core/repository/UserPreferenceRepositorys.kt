package com.example.core.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository3 {
    suspend fun isDarkMode(value: Boolean)
    suspend fun getDarkModeFlow(): Flow<Boolean>
}