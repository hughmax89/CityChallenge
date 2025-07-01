package com.aguerodev.citychallenge.data.datasource.network

import com.aguerodev.citychallenge.data.response.CityResponse
import retrofit2.http.GET

interface CitiesClient {

    @GET("cities.json")
    suspend fun getCities(): List<CityResponse>


    //Mock API
//    @GET("cityList/City")
//    suspend fun getCities(): List<CityResponse>

}