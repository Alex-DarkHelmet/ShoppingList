package com.alex_cutnet.shoppinglist.domain.useCase

import com.alex_cutnet.shoppinglist.domain.ShopItem
import com.alex_cutnet.shoppinglist.domain.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem> {
        return shopListRepository.getShopList()
    }
}