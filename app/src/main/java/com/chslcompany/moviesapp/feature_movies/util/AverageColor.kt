package com.chslcompany.moviesapp.feature_movies.util

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap

fun getAverageColor(imageBitmap: ImageBitmap): Color {
    val compatibleBitmap = imageBitmap.asAndroidBitmap()
        .copy(Bitmap.Config.ARGB_8888, false)

    val pixels = IntArray(compatibleBitmap.width * compatibleBitmap.height)
    compatibleBitmap.getPixels(
        pixels, 0, compatibleBitmap.width, 0, 0,
        compatibleBitmap.width, compatibleBitmap.height
    )

    var redSum = 0
    var greenSum = 0
    var blueSum = 0

    for (pixel in pixels) {
        val red = android.graphics.Color.red(pixel)
        val green = android.graphics.Color.green(pixel)
        val blue = android.graphics.Color.blue(pixel)

        redSum += red
        greenSum += green
        blueSum += blue
    }

    val pixelCount = pixels.size
    val averageRed = redSum / pixelCount
    val averageGreen = greenSum / pixelCount
    val averageBlue = blueSum / pixelCount

    return Color(averageRed, averageGreen, averageBlue)
}
