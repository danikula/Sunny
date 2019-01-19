package com.danikula.sunny.model

data class City(
    val id: Int,
    val name: String,
    val country: String
) {
    fun nameWithCountry(): String {
        return "$name, $country"
    }
}
