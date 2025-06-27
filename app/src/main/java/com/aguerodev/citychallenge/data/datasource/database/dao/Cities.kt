package com.aguerodev.citychallenge.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aguerodev.citychallenge.data.datasource.database.entities.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<CityEntity>)

    @Query("SELECT * FROM city_table")
    suspend fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM CITY_TABLE WHERE name LIKE '%' || :cityName || '%'")
    suspend fun getCitiesByName(cityName: String): List<CityEntity>

    @Query("SELECT * FROM CITY_TABLE WHERE favorite = :favorite")
    suspend fun getFavoriteCities(favorite: Boolean) : List<CityEntity>

    @Query("SELECT * FROM CITY_TABLE WHERE _id = :id")
    suspend fun getCityById(id: Long): CityEntity

    @Query("UPDATE CITY_TABLE SET favorite = 1 WHERE _id = :cityId")
    suspend fun insertFavoriteCity(cityId: Long)

    @Query("UPDATE CITY_TABLE SET favorite = 0 WHERE _id = :cityId")
    suspend fun removeFavoriteCity(cityId: Long)
}
