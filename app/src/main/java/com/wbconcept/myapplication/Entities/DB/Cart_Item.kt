package com.wbconcept.myapplication.Entities.DB

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "carts"
)
 data class Cart_Item(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val color: String,
    val size: String,
    val product_id: Int,
    val quantity: Int,
) : Serializable