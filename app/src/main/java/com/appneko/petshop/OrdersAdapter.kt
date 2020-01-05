package com.appneko.petshop

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.ProductsModel

class OrdersAdapter(private val orders : ArrayList<Orders>) : RecyclerView.Adapter<OrdersAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val order = orders[position]

        holder.id.text = order.id.toString()
        holder.name.text = order.productName
        holder.category.text = order.productCategory
        holder.price.text = order.price.toString()
        holder.amount.text = order.amount.toString()
        holder.total.text = (order.price * order.amount).toString()
        holder.address.text = "Alamatnay " + order.address

    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id : TextView = itemView.findViewById(R.id.item_order_id)
        var name : TextView = itemView.findViewById(R.id.item_order_product_name)
        var price : TextView = itemView.findViewById(R.id.item_order_product_price)
        var category : TextView = itemView.findViewById(R.id.item_order_product_category)
        var amount : TextView = itemView.findViewById(R.id.item_order_amount)
        var total : TextView = itemView.findViewById(R.id.item_order_total)
        var address : TextView = itemView.findViewById(R.id.item_order_address)
    }
}