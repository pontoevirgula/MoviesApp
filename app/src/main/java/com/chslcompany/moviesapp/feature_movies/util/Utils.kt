package com.chslcompany.moviesapp.feature_movies.util

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.chslcompany.moviesapp.feature_movies.util.UIManager.isDarkMode

@SuppressLint("ComposableNaming")
@Composable
fun SetupTextSwitch(): String {
    val text = remember(isDarkMode.value) {
        if (isDarkMode.value) {
            "Ativar modo light"
        } else {
            "Ativar modo dark"
        }
    }
    return text
}
@Composable
fun SetupIconSwitch() {
    val icon = remember(isDarkMode.value) {
        if (isDarkMode.value) {
            Icons.Filled.Nightlight
        } else {
            Icons.Filled.WbSunny
        }
    }
    Icon(icon, contentDescription = null)
}

object UIManager {
    var isDarkMode = mutableStateOf(false)
}