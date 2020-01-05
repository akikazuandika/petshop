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

class ProductsAdapter(private val products : ArrayList<Products>) : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
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
        var context = holder.itemView.context

        holder.btnDelete.setOnClickListener {
            val db = MyDatabaseOpenHelper(context).writableDatabase

            var query = "DELETE FROM ${ProductsModel.TABLE_NAME} WHERE id=${product.id}"
            var result : Cursor = db.rawQuery(query, null)
            var isDeleted : Boolean = result.count <= 0
            if (isDeleted){
                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Gagal", Toast.LENGTH_LONG).show()
            }
        }

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, EditProductActivity::class.java)
            intent.putExtra("id", product.id.toString())
            intent.putExtra("name", product.name)
            intent.putExtra("category", product.category)
            intent.putExtra("price", product.price.toString())
            context.startActivity(intent)
        }
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.item_product_name)
        var price : TextView = itemView.findViewById(R.id.item_product_price)
        var category : TextView = itemView.findViewById(R.id.item_product_category)
        var btnEdit : Button = itemView.findViewById(R.id.item_product_btn_edit)
        var btnDelete : Button = itemView.findViewById(R.id.item_product_btn_delete)
    }
}