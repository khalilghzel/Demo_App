package com.wbconcept.myapplication.Entities.DB

import java.io.Serializable


class Cart_Object(
    var color: String,
    var size: String,
    var quantity: Int,
    var image: String,
    var price: Double,
    var title: String,
    var category: String,
    var id: Int,
    var cart_id: Int,
    var product_id: Int,
    var description: String,
    var selected: Boolean = false
) : Serializable {
    fun equalss(other: Cart_Object): Boolean {
        var rez: Boolean = true
        if (this.color == other.color)
            rez = false
        if (this.size == other.size)
            rez = false
        if (this.quantity == other.quantity)
            rez = false
        if (this.image == other.image)
            rez = false
        if (this.price == other.price)
            rez = false
        if (this.title == other.title)
            rez = false
        if (this.category == other.category)
            rez = false
        if (this.id == other.id)
            rez = false
        if (this.cart_id == other.cart_id)
            rez = false
        if (this.product_id == other.product_id)
            rez = false
        if (this.description == other.description)
            rez = false
        if (this.description == other.description)
            rez = false

        return rez
    }
}