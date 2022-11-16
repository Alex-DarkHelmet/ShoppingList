package com.alex_cutnet.shoppinglist.scenes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex_cutnet.shoppinglist.data.ShopListRepositoryImpl
import com.alex_cutnet.shoppinglist.domain.ShopItem
import com.alex_cutnet.shoppinglist.domain.useCase.AddShopItemUseCase
import com.alex_cutnet.shoppinglist.domain.useCase.EditShopItemUseCase
import com.alex_cutnet.shoppinglist.domain.useCase.GetShopItemUseCase

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validField = validateInput(name, count)
        if (validField) {
            val shopItem = ShopItem(name, count, true)
            editShopItemUseCase.editShopItem(shopItem)
            closeScreen()
        }
    }

    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addEditedShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validField = validateInput(name, count)
        if (validField) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                addShopItemUseCase.addShopItem(item)
                closeScreen()
            }

        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun resetErrorInputName() {
        _errorInputName.value = false
    }

    private fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun closeScreen(){
        _closeScreen.value = Unit
    }
}