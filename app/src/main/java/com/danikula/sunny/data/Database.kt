package com.danikula.sunny.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.danikula.sunny.model.City
import com.danikula.sunny.model.Forecast

/**
 * Sqlite database managed by Room.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
@Database(entities = [City::class, Forecast::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun forecastDao(): ForecastDao
}