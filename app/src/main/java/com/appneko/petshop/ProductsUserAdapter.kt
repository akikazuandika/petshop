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

class ProductsUserAdapter(private val products : ArrayList<Products>) : RecyclerView.Adapter<ProductsUserAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_product_user, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        println(products.size)
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.name.text = "Nama : " + product.name
        holder.category.text = "Kategori : " + product.category
        holder.price.text = "Harga : " + product.price.toString()

        holder.btnBuy.setOnClickListener {

        }
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.item_product_name)
        var price : TextView = itemView.findViewById(R.id.item_product_price)
        var category : TextView = itemView.findViewById(R.id.item_product_category)
        var btnBuy : Button = itemView.findViewById(R.id.item_product_btn_buy)
    }
}