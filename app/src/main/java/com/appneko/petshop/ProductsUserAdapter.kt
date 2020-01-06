package com.appneko.petshop

import android.content.Context
import android.content.Intent
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
        holder.name.text = product.name
        holder.category.text = product.category
        holder.price.text = product.price.toString()
        var context = holder.itemView.context

        holder.btnBuy.setOnClickListener {
            var intent = Intent(context, BuyActivity::class.java)
            intent.putExtra("product_id", product.id.toString())
            intent.putExtra("product_name", product.name)
            intent.putExtra("product_category", product.category)
            intent.putExtra("product_price", product.price.toString())
            context.startActivity(intent)
        }
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.item_product_name)
        var price : TextView = itemView.findViewById(R.id.item_product_price)
        var category : TextView = itemView.findViewById(R.id.item_product_category)
        var btnBuy : Button = itemView.findViewById(R.id.item_product_btn_buy)
    }
}