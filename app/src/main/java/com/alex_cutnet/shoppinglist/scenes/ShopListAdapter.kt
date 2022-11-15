package com.alex_cutnet.shoppinglist.scenes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.alex_cutnet.shoppinglist.R
import com.alex_cutnet.shoppinglist.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType) {
            VIEW_TYPE_ENABLE -> R.layout.shop_item_enable
            VIEW_TYPE_DISABLE -> R.layout.shop_item_disable
            else -> throw java.lang.RuntimeException("Unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()

        viewHolder.view.setOnLongClickListener{
            onShopItemClick?.invoke(shopItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if (item.enable) {
            VIEW_TYPE_ENABLE
        } else VIEW_TYPE_DISABLE
    }

    companion object {
        const val VIEW_TYPE_DISABLE = 1
        const val VIEW_TYPE_ENABLE = 0
        const val MAX_POOL_SIZE = 16
    }
}