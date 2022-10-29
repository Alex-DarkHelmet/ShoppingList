package com.alex_cutnet.shoppinglist.domain.useCase

import com.alex_cutnet.shoppinglist.domain.ShopItem
import com.alex_cutnet.shoppinglist.domain.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }
}