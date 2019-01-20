package com.danikula.sunny.data

import android.arch.persistence.room.*
import com.danikula.sunny.model.City

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun queryAllCities(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Delete
    fun deleteCity(city: City)
}
