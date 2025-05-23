package com.chslcompany.moviesapp.feature_movies.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.chslcompany.moviesapp.BuildConfig
import com.chslcompany.moviesapp.feature_movies.presentation.favorites.viewmodel.FavoriteViewModel
import com.chslcompany.moviesapp.feature_movies.util.RatingBar
import com.chslcompany.moviesapp.feature_movies.util.Screens
import com.chslcompany.moviesapp.feature_movies.util.getAverageColor
import com.example.core.model.Movie

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieItem(
    bottomNavController: NavHostController? = null,
    movie: Movie,
    navHostController: NavHostController,
    favoriteViewModel: FavoriteViewModel? = null
) {
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(BuildConfig.IMAGE_BASE_URL + movie.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember { mutableStateOf(defaultColor) }

    val gradientBrush = remember(dominantColor, defaultColor) {
        Brush.verticalGradient(
            colors = listOf(
                defaultColor,
                dominantColor
            )
        )
    }

    val onClick: () -> Unit = remember(movie.id) {
        { navHostController.navigate(Screens.Details.rout + "/${movie.id}") }
    }

    val onLongClick: () -> Unit = remember(movie, bottomNavController) {
        {
            favoriteViewModel?.addFavorite(movie)
            bottomNavController?.navigate(Screens.FavoriteMovieList.rout)
        }
    }

    val onDoubleClick: () -> Unit = remember(movie, navHostController) {
        {
            favoriteViewModel?.removeFavorite(movie)
            navHostController.navigate(Screens.Home.rout)
        }
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(gradientBrush)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick
            )
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = movie.title
                )
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            LaunchedEffect(imageState) {
                dominantColor = getAverageColor(
                    imageBitmap = imageState.result.drawable.toBitmap().asImageBitmap()
                )
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = imagePainter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = movie.title,
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = movie.vote_average / 2
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = movie.vote_average.toString().take(3),
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1,
            )
        }
    }
}
