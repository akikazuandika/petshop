package com.appneko.petshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.OrdersModel
import com.appneko.petsho.ProductsModel
import com.appneko.petsho.UsersModel
import com.google.android.material.snackbar.Snackbar

class OrdersActivity : AppCompatActivity() {
    var list : ArrayList<Orders> = ArrayList()
    private lateinit var rvOrders : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val sharedPreference =  getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val type = sharedPreference.getString("type", null)
        val username = sharedPreference.getString("username", null)

        fetchData(this, type.toString(), username)
        refreshList(this, type.toString())
    }

    fun fetchData(ctx : Context, type: String, username: String?){
        var db = MyDatabaseOpenHelper(ctx).readableDatabase
        var query = "SELECT ${OrdersModel.TABLE_NAME}.*, ${ProductsModel.TABLE_NAME}.${ProductsModel.NAME}, ${ProductsModel.TABLE_NAME}.${ProductsModel.CATEGORY}, ${CategoriesModel.TABLE_NAME}.${CategoriesModel.NAME} as `categoryName`, ${OrdersModel.TABLE_NAME}.${OrdersModel.USERNAME} as `usernameBuy` FROM ${OrdersModel.TABLE_NAME} JOIN ${ProductsModel.TABLE_NAME} ON ${ProductsModel.TABLE_NAME}.${ProductsModel.ID} = ${OrdersModel.TABLE_NAME}.${OrdersModel.ID_PRODUCT} JOIN ${CategoriesModel.TABLE_NAME} ON ${CategoriesModel.TABLE_NAME}.${CategoriesModel.ID} = ${ProductsModel.TABLE_NAME}.${ProductsModel.CATEGORY} ORDER BY ${OrdersModel.ID} DESC"

        if (type == "user"){
            query = "SELECT ${OrdersModel.TABLE_NAME}.*, ${ProductsModel.TABLE_NAME}.${ProductsModel.NAME}, ${ProductsModel.TABLE_NAME}.${ProductsModel.CATEGORY}, ${CategoriesModel.TABLE_NAME}.${CategoriesModel.NAME} as `categoryName`, ${OrdersModel.TABLE_NAME}.${OrdersModel.USERNAME} as `usernameBuy` FROM ${OrdersModel.TABLE_NAME} JOIN ${ProductsModel.TABLE_NAME} ON ${ProductsModel.TABLE_NAME}.${ProductsModel.ID} = ${OrdersModel.TABLE_NAME}.${OrdersModel.ID_PRODUCT} JOIN ${CategoriesModel.TABLE_NAME} ON ${CategoriesModel.TABLE_NAME}.${CategoriesModel.ID} = ${ProductsModel.TABLE_NAME}.${ProductsModel.CATEGORY} WHERE ${OrdersModel.USERNAME}='${username}' ORDER BY ${OrdersModel.ID} DESC"
        }

        var result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                var orders = Orders()
                orders.id = result.getString(0).toString().toInt()
                orders.idProduct = result.getString(2).toString()
                orders.username = result.getString(11).toString()
                orders.amount = result.getString(3).toString().toInt()
                orders.price = result.getString(4).toString().toInt()
                orders.address = result.getString(5).toString()
                orders.date = result.getString(7).toString()
                orders.productName = result.getString(8).toString()
                orders.productCategory = result.getString(10).toString()
                orders.status = result.getString(6).toString()
                list.add(orders)
            }while (result.moveToNext())
        }
    }

    fun refreshList(ctx: Context, type : String){
        rvOrders = findViewById(R.id.rv_orders)
        rvOrders.setHasFixedSize(true)
        rvOrders.layoutManager = LinearLayoutManager(ctx)
        val listCategoryAdapter = OrdersAdapter(list)

        if (type == "admin"){
            var listCategoryAdapter = OrdersAdminAdapter(list)
            rvOrders.adapter = listCategoryAdapter
        }else{
            var listCategoryAdapter = OrdersAdapter(list)
            rvOrders.adapter = listCategoryAdapter
        }
    }
}
