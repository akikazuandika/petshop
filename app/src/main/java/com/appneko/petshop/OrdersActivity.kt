package com.appneko.petshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.OrdersModel
import com.appneko.petsho.ProductsModel

class OrdersActivity : AppCompatActivity() {
    var list : ArrayList<Orders> = ArrayList()
    private lateinit var rvOrders : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        fetchData(this)
        refreshList(this)
    }

    fun fetchData(ctx : Context){
        var db = MyDatabaseOpenHelper(ctx).readableDatabase

        var result = db.rawQuery("SELECT ${OrdersModel.TABLE_NAME}.*, ${ProductsModel.TABLE_NAME}.${ProductsModel.NAME}, ${ProductsModel.TABLE_NAME}.${ProductsModel.CATEGORY} FROM ${OrdersModel.TABLE_NAME} JOIN ${ProductsModel.TABLE_NAME} ON ${ProductsModel.TABLE_NAME}.${ProductsModel.ID} = ${OrdersModel.TABLE_NAME}.${OrdersModel.ID_PRODUCT}", null)
        if (result.moveToFirst()){
            do {
                var orders = Orders()
                orders.id = result.getString(0).toString().toInt()
                orders.idProduct = result.getString(1).toString()
                orders.username = result.getString(2).toString()
                orders.amount = result.getString(3).toString().toInt()
                orders.price = result.getString(4).toString().toInt()
                orders.address = result.getString(5).toString()
                orders.date = result.getString(6).toString()
                orders.productName = result.getString(7).toString()
                orders.productCategory = result.getColumnIndex("${ProductsModel.CATEGORY}").toString()
                list.add(orders)
            }while (result.moveToNext())
        }



    }

    fun refreshList(ctx: Context){
        rvOrders = findViewById(R.id.rv_orders)
        rvOrders.setHasFixedSize(true)
        rvOrders.layoutManager = LinearLayoutManager(ctx)
        val listCategoryAdapter = OrdersAdapter(list)
        rvOrders.adapter = listCategoryAdapter
    }
}
