package com.chslcompany.moviesapp.feature_movies.data.local.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("select * from favoriteentity")
    fun listFavorites() : List<FavoriteEntity>

    @Delete
    suspend fun deleteFavoriteById(entity: FavoriteEntity)
}