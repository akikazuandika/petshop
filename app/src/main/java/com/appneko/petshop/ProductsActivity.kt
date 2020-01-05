package com.appneko.petshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.ProductsModel
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity() {
    private lateinit var rvProducts : RecyclerView
    var list : ArrayList<Products> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        rvProducts = findViewById(R.id.rv_products)
        rvProducts.setHasFixedSize(true)

        val sharedPreference =  getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val type = sharedPreference.getString("type", null)

        if (type != "admin"){
            product_btn_to_add.visibility = View.GONE
        }

        fetchData(this)
        refreshList(this, type.toString())

        product_btn_to_add.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun fetchData(ctx : Context){
        val db = MyDatabaseOpenHelper(ctx).readableDatabase

        var query = "SELECT ${ProductsModel.TABLE_NAME}.*, ${CategoriesModel.TABLE_NAME}.${ProductsModel.NAME} as `categoryName` FROM ${ProductsModel.TABLE_NAME} JOIN ${CategoriesModel.TABLE_NAME} ON ${CategoriesModel.TABLE_NAME}.${CategoriesModel.ID} = ${ProductsModel.TABLE_NAME}.${ProductsModel.CATEGORY}"
        var result = db.rawQuery(query, null)

        if (result.moveToFirst()){
            do {
                var product = Products()
                product.id = result.getString(0).toInt()
                product.name = result.getString(1).toString()
                product.category = result.getString(4).toString()
                product.price = result.getString(3).toInt()
                list.add(product)
            }while (result.moveToNext())
        }
    }

    fun refreshList(ctx: Context, type : String){
        rvProducts = findViewById(R.id.rv_products)
        rvProducts.setHasFixedSize(true)
        rvProducts.layoutManager = LinearLayoutManager(ctx)
        if (type == "admin"){
            var listCategoryAdapter = ProductsAdapter(list)
            rvProducts.adapter = listCategoryAdapter
        }else{
            var listCategoryAdapter = ProductsUserAdapter(list)
            rvProducts.adapter = listCategoryAdapter
        }

    }
}
