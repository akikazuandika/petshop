package com.appneko.petshop

import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel

class CategoriesAdapter(private val categories : ArrayList<Categories>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categories[position]
        holder.name.text = "Nama : " + category.name
        var context = holder.itemView.context

        holder.btnDelete.setOnClickListener {
            val db = MyDatabaseOpenHelper(context).writableDatabase

            var query = "DELETE FROM ${CategoriesModel.TABLE_NAME} WHERE id=${category.id}"
            var result : Cursor = db.rawQuery(query, null)
            var isDeleted : Boolean = result.count <= 0
            if (isDeleted){
                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, "Gagal", Toast.LENGTH_LONG).show()
            }
        }

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, EditCategoryActivity::class.java)
            intent.putExtra("name", category.name)
            intent.putExtra("id", category.id.toString())
            context.startActivity(intent)
        }
    }

    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.item_category_name)
        var btnEdit : Button = itemView.findViewById(R.id.item_category_btn_edit)
        var btnDelete : Button = itemView.findViewById(R.id.item_category_btn_delete)
    }
}