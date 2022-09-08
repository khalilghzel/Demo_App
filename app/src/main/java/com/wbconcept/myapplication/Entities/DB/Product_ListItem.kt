package com.wbconcept.myapplication.Entities.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wbconcept.myapplication.Entities.Rating
import java.io.Serializable

@Entity(
    tableName = "products"
)
 data class Product_ListItem(
    val category: String,
    val description: String,
    @PrimaryKey()
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val rating: Rating = Rating(0,0.0),
    val favorite:Boolean = false

) : Serializable