package com.wbconcept.myapplication.Entities

import java.io.Serializable

data class Rating(
    val count: Int,
    val rate: Double
): Serializable