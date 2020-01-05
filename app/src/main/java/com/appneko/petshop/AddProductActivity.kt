package com.appneko.petshop

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.ProductsModel
import com.google.android.material.snackbar.Snackbar
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

            var cv = ContentValues()
            cv.put(ProductsModel.NAME, name)
            cv.put(ProductsModel.PRICE, price)
            cv.put(ProductsModel.CATEGORY, categoryId)

            var resultInsertProduct = dbWritable.insert(ProductsModel.TABLE_NAME, null, cv)

            if (resultInsertProduct == (-1).toLong()){
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                var snackbar = Snackbar.make(it, "Failed", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                var snackbar = Snackbar.make(it, "Sucess", Snackbar.LENGTH_SHORT)
                snackbar.show()

                var intent = Intent(this@AddProductActivity, ProductsActivity::class.java)

                Timer("Done", false).schedule(1000){
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
