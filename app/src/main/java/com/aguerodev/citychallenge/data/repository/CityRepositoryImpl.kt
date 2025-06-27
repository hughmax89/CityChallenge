package com.aguerodev.citychallenge.data.repository

import com.aguerodev.citychallenge.data.datasource.database.AppDatabase
import com.aguerodev.citychallenge.data.datasource.database.entities.toDomain
import com.aguerodev.citychallenge.data.datasource.network.CitiesClient
import com.aguerodev.citychallenge.data.response.toDomain
import com.aguerodev.citychallenge.data.response.toEntity
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.domain.repository.CityRepository
import javax.inject.Inject


class CityRepositoryImpl @Inject constructor(private val client: CitiesClient, private val appDatabase: AppDatabase): CityRepository {

    override suspend fun getCitiesFromApi() {
        var getCitiesFromApi = client.getCities()
        var getCitiesFromDB = appDatabase.cityDao().getAllCities()
        if (getCitiesFromDB.isEmpty()){
            appDatabase.cityDao().insertCities(getCitiesFromApi.map { it.toEntity() })
        }
    }

    override suspend fun getAllCitiesFromDb(): List<City> {
        return appDatabase.cityDao().getAllCities().map { it.toDomain() }
    }

    override suspend fun getCitiesByName(name: String): List<City> {
        return appDatabase.cityDao().getCitiesByName(name).map { it.toDomain() }.sortedBy { it.name }
    }

    override suspend fun getCitiesFavoriteList(): List<City> {
        return appDatabase.cityDao().getFavoriteCities(true).map { it.toDomain() }
    }

    override suspend fun getCity(_id: Long): City {
        return appDatabase.cityDao().getCityById(_id).toDomain()
    }

    override suspend fun insertFavoriteCity(_id: Long) {
        appDatabase.cityDao().insertFavoriteCity(_id)
    }

    override suspend fun removeFavoriteCity(_id: Long) {
        appDatabase.cityDao().removeFavoriteCity(_id)
    }
}

