package com.aguerodev.citychallenge.domain.usecase

import com.aguerodev.citychallenge.data.repository.CityRepositoryImpl
import com.aguerodev.citychallenge.domain.entity.City
import javax.inject.Inject

class GetCitiesByNameUseCase @Inject constructor(val repository: CityRepositoryImpl) {
    suspend operator fun invoke(name: String): List<City>?{
        return repository.getCitiesByName(name)
    }
}