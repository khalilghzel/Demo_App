package com.wbconcept.myapplication.Entities

import com.wbconcept.myapplication.Entities.DB.Product_ListItem

class Product_List : ArrayList<Product_ListItem>(){
    fun getList():List<Product_ListItem>{
        return this.toList()
    }
}