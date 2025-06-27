package com.aguerodev.citychallenge.data.datasource.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aguerodev.citychallenge.domain.entity.City
import com.aguerodev.citychallenge.domain.entity.Coordinates


@Entity(tableName = "city_table")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("country") val country: String,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("_id") val _id: Long,
    @ColumnInfo("coordinates") val coord: String,
    @ColumnInfo("favorite") val favorite: Boolean
)

fun CityEntity.toDomain(): City {
    return City(
        id = id,
        country = country,
        name = name,
        _id = _id,
        coord = coord.toCoordinates(),
        favorite = favorite
    )
}

fun String.toCoordinates(): Coordinates {
    val (lon, lat) = this.split(",").map { it.toDouble() }
    return Coordinates(lon, lat)
}