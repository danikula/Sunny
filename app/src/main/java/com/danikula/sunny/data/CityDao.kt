package com.danikula.sunny.data

import android.arch.persistence.room.*
import com.danikula.sunny.model.City
import io.reactivex.Flowable

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun queryAllCities(): Flowable<List<City>>

    @Query("SELECT COUNT(id) FROM city")
    fun queryCitiesCount(): Flowable<Int>

    @Query("SELECT * FROM city WHERE id = :cityId")
    fun queryCity(cityId: Long): Flowable<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City): Long

    @Delete
    fun deleteCity(city: City): Int
}
