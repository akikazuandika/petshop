package com.appneko.petshop

import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var rvCategories : RecyclerView
    var list : ArrayList<Categories> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        rvCategories = findViewById(R.id.rv_categories)
        rvCategories.setHasFixedSize(true)

        fetchData(this)
        refreshList(this)

        category_btn_add.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        }
    }

    fun fetchData(ctx : Context){
        val db = MyDatabaseOpenHelper(ctx).readableDatabase

        var query = "SELECT * FROM ${CategoriesModel.TABLE_NAME}"
        var result = db.rawQuery(query, null)

        if (result.moveToFirst()){
            do {
                var category = Categories()
                category.id = result.getString(0).toInt()
                category.name = result.getString(1).toString()
                list.add(category)
            }while (result.moveToNext())
        }
    }

    fun refreshList(ctx: Context){
        rvCategories = findViewById(R.id.rv_categories)
        rvCategories.setHasFixedSize(true)
        rvCategories.layoutManager = LinearLayoutManager(ctx)
        val listCategoryAdapter = CategoriesAdapter(list)
        rvCategories.adapter = listCategoryAdapter
    }

    fun deleteCategory(context : Context, id : Int){
        val db = MyDatabaseOpenHelper(context).writableDatabase

        db.execSQL("DELETE FROM ${CategoriesModel.TABLE_NAME} WHERE id=${id}")
        killActivity()
    }

    fun killActivity(){
        finish()
    }
}
