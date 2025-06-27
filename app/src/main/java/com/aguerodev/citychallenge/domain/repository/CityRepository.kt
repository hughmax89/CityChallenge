package com.aguerodev.citychallenge.domain.repository

import com.aguerodev.citychallenge.data.datasource.database.entities.CityEntity
import com.aguerodev.citychallenge.domain.entity.City


interface CityRepository {
    suspend fun getCitiesFromApi()
    suspend fun getAllCitiesFromDb(): List<City>
    suspend fun getCitiesByName(name: String): List<City>
    suspend fun getCitiesFavoriteList() : List<City>
    suspend fun getCity(_id: Long): City
    suspend fun insertFavoriteCity(_id: Long)
    suspend fun removeFavoriteCity(_id: Long)
}