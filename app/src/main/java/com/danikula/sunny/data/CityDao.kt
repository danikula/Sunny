package com.danikula.sunny.data

import android.arch.persistence.room.*
import com.danikula.sunny.model.City
import io.reactivex.Single

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun queryAllCities(): Single<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City): Long

    @Delete
    fun deleteCity(city: City): Int
}
