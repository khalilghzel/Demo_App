package com.wbconcept.myapplication.DB

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.wbconcept.myapplication.Entities.Rating

class MyTypeConverters {


    @TypeConverter
    fun fromRatingToJSON(rating: Rating): String {
        return Gson().toJson(rating)
    }
    @TypeConverter
    fun fromJSONToRating(json: String): Rating {
        return Gson().fromJson(json,Rating::class.java)
    }
}