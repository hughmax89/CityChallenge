package com.aguerodev.citychallenge.domain.entity

import android.os.Parcelable
import com.aguerodev.citychallenge.data.datasource.database.entities.CityEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int,
    val country: String,
    val name: String,
    val _id: Long,
    val coord: Coordinates,
    val favorite: Boolean
) : Parcelable


@Parcelize
data class Coordinates(
    val lon: Double,
    val lat: Double
) : Parcelable

fun City.toEntity(): CityEntity {
    return CityEntity(
        id = id,
        country = country,
        name = name,
        _id = _id,
        coord = coord.asString(),
        favorite = false
    )
}

fun Coordinates.asString() : String{
    return "$lon,$lat"
}

