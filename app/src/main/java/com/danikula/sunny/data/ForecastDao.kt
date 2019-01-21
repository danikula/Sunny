package com.danikula.sunny.data

import android.arch.persistence.room.*
import com.danikula.sunny.model.Forecast
import io.reactivex.Flowable

/**
 * DAO for [Forecast]
 * @author Alexey Danilov (danikula@gmail.com).
 */
@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast WHERE city_id = :cityId")
    fun queryForCity(cityId: Long): Flowable<List<Forecast>>

    @Query("DELETE FROM forecast WHERE city_id = :cityId")
    fun deleteForCity(cityId: Long)

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertMany(forecasts: List<Forecast>)

    @Transaction
    fun updateForCity(cityId: Long, forecasts: List<Forecast>) {
        deleteForCity(cityId)
        insertMany(forecasts)
    }
}
