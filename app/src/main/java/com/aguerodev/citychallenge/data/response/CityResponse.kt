package com.aguerodev.citychallenge.data.response


import com.aguerodev.citychallenge.data.datasource.database.entities.CityEntity
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.domain.entity.Coordinates
import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    val country: String,
    val name: String,
    val _id: Long,
    val coord: CoordinatesResponse
)


@Serializable
data class CoordinatesResponse(
    val lon: Double,
    val lat: Double
)

fun CityResponse.toEntity(): CityEntity {
    return CityEntity(
        id = 0,
        country = country,
        name = name,
         _id = _id,
        coord = coord.asString(),
        favorite = false
    )
}

fun CoordinatesResponse.asString() : String{
    return "$lon,$lat"
}

fun CityResponse.toDomain(): City {
    return City(
        id = 0,
        country = country,
        name = name,
        _id = _id,
        coord = Coordinates(lon = coord.lon, lat = coord.lat),
        favorite = false
    )
}