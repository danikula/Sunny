package com.danikula.sunny.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "forecast", foreignKeys = [
        ForeignKey(entity = City::class, parentColumns = ["id"], childColumns = ["city_id"], onDelete = CASCADE)
    ]
)
data class Forecast(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "city_id", index = true)
    val cityId: Long,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "temperature")
    val temperature: Int
) {
    companion object {
        val DATE_FORMAT = SimpleDateFormat("dd MMMM HH:mm", Locale.US)
    }

    fun dateTime(): String {
        return DATE_FORMAT.format(Date(timestamp))
    }
}
