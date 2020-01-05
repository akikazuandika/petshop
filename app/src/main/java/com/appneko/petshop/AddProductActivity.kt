package com.appneko.petshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.ProductsModel
import kotlinx.android.synthetic.main.activity_add_product.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class AddProductActivity : AppCompatActivity() {

    var list : ArrayList<String> = ArrayList()
    var categoryId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val db = MyDatabaseOpenHelper(this).readableDatabase
        var query = "SELECT * FROM ${CategoriesModel.TABLE_NAME}"
        var result = db.rawQuery(query, null)

        if (result.moveToFirst()){
            do {
                list.add(result.getString(1).toString())
            }while (result.moveToNext())
        }

        var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        addProduct_spinner.adapter = adapter

        addProduct_btn_add.setOnClickListener {
            var name = addProduct_inp_name.text.toString()
            var price = addProduct_inp_price.text.toString().toInt()
            var category = addProduct_spinner.selectedItem.toString()

            var result = db.rawQuery("SELECT * FROM ${CategoriesModel.TABLE_NAME} WHERE name='${category}'", null)

            if (result.moveToFirst()){
                do {
                    categoryId = result.getString(0).toString()
                }while (result.moveToNext())
            }

            val dbWritable = MyDatabaseOpenHelper(this).writableDatabase
            var resultInsertProduct = dbWritable.rawQuery("INSERT INTO ${ProductsModel.TABLE_NAME}(name, category, price) VALUES('${name}', '${categoryId}', '${price}')", null)
            if (resultInsertProduct.moveToFirst()){
                if (resultInsertProduct.getString(0).toString() != "-1"){
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show()
                    Timer("Done", false).schedule(1000){
                        finish()
                    }
                }else{
                    Toast.makeText(this, "Gagal", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
