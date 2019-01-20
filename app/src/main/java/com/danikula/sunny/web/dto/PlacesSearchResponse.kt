package com.danikula.sunny.web.dto

import com.danikula.sunny.model.City

data class PlacesSearchResponse(val list: List<Place>)

data class Place(val id: Long, val name: String, val sys: PlaceCountry) {
    fun toCity(): City {
        return City(id, name, sys.country)
    }
}

data class PlaceCountry(val country: String)