package com.aguerodev.citychallenge.domain.usecase

import com.aguerodev.citychallenge.data.repository.CityRepositoryImpl
import com.aguerodev.citychallenge.domain.entity.City
import javax.inject.Inject

class GetFavoriteCitiesUseCase @Inject constructor(val repository: CityRepositoryImpl) {
    suspend operator fun invoke(): List<City>?{
        return repository.getCitiesFavoriteList()
    }
}