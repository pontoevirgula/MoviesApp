package com.chslcompany.moviesapp.feature_movies.util

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@SuppressLint("ComposableNaming")
@Composable
fun SetupTextSwitch(isDarkMode: Boolean): String {
    val text = remember(isDarkMode) {
        if (isDarkMode) {
            "Ativar modo light"
        } else {
            "Ativar modo dark"
        }
    }
    return text
}
@Composable
fun SetupIconSwitch(isDarkMode: Boolean) {
    val icon = remember(isDarkMode) {
        if (isDarkMode) {
            Icons.Filled.Nightlight
        } else {
            Icons.Filled.WbSunny
        }
    }
    Icon(icon, contentDescription = null)
}
