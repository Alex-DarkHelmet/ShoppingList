package com.alex_cutnet.shoppinglist.scenes

import androidx.lifecycle.ViewModel
import com.alex_cutnet.shoppinglist.data.ShopListRepositoryImpl
import com.alex_cutnet.shoppinglist.domain.ShopItem
import com.alex_cutnet.shoppinglist.domain.useCase.DeleteShopItemUseCase
import com.alex_cutnet.shoppinglist.domain.useCase.EditShopItemUseCase
import com.alex_cutnet.shoppinglist.domain.useCase.GetShopListUseCase

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()


    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)

    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enable = !shopItem.enable)
        editShopItemUseCase.editShopItem(newItem)
    }
}