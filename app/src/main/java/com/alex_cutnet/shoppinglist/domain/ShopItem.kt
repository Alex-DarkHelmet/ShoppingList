package com.alex_cutnet.shoppinglist.domain


data class ShopItem(
    val id: Int,
    val name: String,
    val count: Int,
    val enable: Boolean
)
