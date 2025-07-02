package com.aguerodev.citychallenge.data.di

import com.aguerodev.citychallenge.data.repository.CityRepositoryImpl
import com.aguerodev.citychallenge.domain.repository.CityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCityRepository(
        impl: CityRepositoryImpl
    ): CityRepository
}