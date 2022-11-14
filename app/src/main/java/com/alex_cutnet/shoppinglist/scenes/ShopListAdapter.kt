package com.alex_cutnet.shoppinglist.scenes

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alex_cutnet.shoppinglist.R
import com.alex_cutnet.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    private var count = 0

    var shopList = listOf<ShopItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            val diffCallback = ShopListDiffCallback(shopList, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("CreateViewHolder", "viewHolder: ${++count}")
        val layout = when(viewType) {
            VIEW_TYPE_ENABLE -> R.layout.shop_item_enable
            VIEW_TYPE_DISABLE -> R.layout.shop_item_disable
            else -> throw java.lang.RuntimeException("Unknown viewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()

        viewHolder.view.setOnLongClickListener{
            onShopItemClick?.invoke(shopItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]

        return if (item.enable) {
            VIEW_TYPE_ENABLE
        } else VIEW_TYPE_DISABLE
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    companion object {
        const val VIEW_TYPE_DISABLE = 1
        const val VIEW_TYPE_ENABLE = 0
        const val MAX_POOL_SIZE = 16
    }
}