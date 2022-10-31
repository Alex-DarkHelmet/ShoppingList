package com.alex_cutnet.shoppinglist.data

import com.alex_cutnet.shoppinglist.domain.ShopItem
import com.alex_cutnet.shoppinglist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private var shopList = mutableListOf<ShopItem>()
    private var addId = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = addId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element is not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}