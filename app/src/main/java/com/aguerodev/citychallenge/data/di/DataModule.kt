package com.aguerodev.citychallenge.data.di

import android.content.Context
import androidx.room.Room
import com.aguerodev.citychallenge.data.datasource.database.AppDatabase
import com.aguerodev.citychallenge.data.datasource.network.CitiesClient
import com.aguerodev.citychallenge.data.response.CityResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val CITY_DATABASE = "city_db"

    @Singleton
    @Provides
    fun provideApiServices(retrofit: Retrofit): CitiesClient {
        return retrofit.create(CitiesClient::class.java)
    }

    //TODO: API Ualá
    @Singleton
    @Provides
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/")
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

//    //TODO: API MOCK
//    @Singleton
//    @Provides
//    fun provideRetrofit(json: Json): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl("https://6864311f88359a373e97c74c.mockapi.io/api/")
//            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
//            .build()
//    }

    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, CITY_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideCityDao(dataBase: AppDatabase) = dataBase.cityDao()
}
