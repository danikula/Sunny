package com.danikula.sunny.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.danikula.sunny.model.City

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun cityDao(): CityDao
}