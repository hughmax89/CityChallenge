package com.aguerodev.citychallenge.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aguerodev.citychallenge.data.datasource.database.dao.CityDao
import com.aguerodev.citychallenge.data.datasource.database.entities.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}
