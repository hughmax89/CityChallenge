package com.aguerodev.citychallenge.domain.usecase


import com.aguerodev.citychallenge.data.repository.CityRepositoryImpl
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.domain.repository.CityRepository
import javax.inject.Inject

class SaveFavoriteCityUseCase @Inject constructor(val repository: CityRepository) {
    suspend operator fun invoke(_id: Long){
            repository.insertFavoriteCity(_id)
    }
}