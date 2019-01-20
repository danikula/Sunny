package com.danikula.sunny.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "city")
data class City(

    @PrimaryKey
    val id: Long,

    val name: String,

    val country: String
) {
    fun nameWithCountry(): String {
        return "$name, $country"
    }
}
