package com.alex_cutnet.shoppinglist.scenes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alex_cutnet.shoppinglist.R
import com.alex_cutnet.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditFinishedListener {

    private var shopItemId = ShopItem.UNDEFINED_ID
    private var screenMode = MODE_UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        if (savedInstanceState == null) {
            launchRightMode()
        }
}
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddShopItem()
            MODE_EDIT -> ShopItemFragment.newInstanceEditShopItem(shopItemId)
            else -> throw RuntimeException("Unknown screen mode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
                throw RuntimeException("Screen mode is absent")
            }
            val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

            if (mode != MODE_EDIT && mode != MODE_ADD) {
                throw RuntimeException("Unknown screen mode")
            }
            screenMode = mode

            if (screenMode == MODE_EDIT) {
                if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                    throw RuntimeException("Shop item id is absent")
                }
                shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
            }
    }
    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

    override fun onEditFinishedListener() {
        finish()
    }
}
