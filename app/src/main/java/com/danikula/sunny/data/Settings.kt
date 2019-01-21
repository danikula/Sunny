package com.danikula.sunny.data

import android.content.SharedPreferences

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class Settings(private val preferences: SharedPreferences) {

    companion object {
        private const val KEY_CITY_ID = "city_id"
    }

    var activeCityId: Long?
        get() = if (preferences.contains(KEY_CITY_ID)) preferences.getLong(KEY_CITY_ID, 0) else null
        set(value) {
            value?.let {
                preferences
                    .edit()
                    .putLong(KEY_CITY_ID, value)
                    .apply()
            }
        }
}
