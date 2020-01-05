package com.appneko.petshop

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.ProductsModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_edit_product.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class EditProductActivity : AppCompatActivity() {

    var listSpinner : ArrayList<String> = ArrayList()
    var categoryId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_product)

        var id = intent.getStringExtra("id")
        var name = intent.getStringExtra("name")
        var category = intent.getStringExtra("category")
        var price = intent.getStringExtra("price")

        val db = MyDatabaseOpenHelper(this).readableDatabase
        var query = "SELECT * FROM ${CategoriesModel.TABLE_NAME}"
        var result = db.rawQuery(query, null)

        if (result.moveToFirst()){
            var position = 0
            do {
                if (result.getString(1).toString() == category){
                    categoryId = position.toString()
                }
                listSpinner.add(result.getString(1).toString())
                position++
            }while (result.moveToNext())
        }

        var adapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSpinner)
        editProduct_spinner.adapter = adapter

        editProduct_spinner.setSelection(categoryId.toInt())
        editProduct_inp_name.setText(name)
        editProduct_inp_price.setText(price)

        editProduct_btn_edit.setOnClickListener {
            var inpName = editProduct_inp_name.text.toString()
            var inpPrice = editProduct_inp_price.text.toString()
            var inpCategory = editProduct_spinner.selectedItem

            var result = db.rawQuery("SELECT * FROM ${CategoriesModel.TABLE_NAME} WHERE name='${inpCategory}'", null)
            if (result.moveToFirst()){
                do {
                    categoryId = result.getString(0).toString()
                }while (result.moveToNext())
            }

            var cv = ContentValues()
            cv.put(ProductsModel.NAME, inpName)
            cv.put(ProductsModel.PRICE, inpPrice.toInt())
            cv.put(ProductsModel.CATEGORY, categoryId)

            db.update(ProductsModel.TABLE_NAME, cv, ProductsModel.ID + "=?", arrayOf(id))

            val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

            var snackbar = Snackbar.make(it, "Sucess", Snackbar.LENGTH_SHORT)
            snackbar.show()

            var intent = Intent(this@EditProductActivity, ProductsActivity::class.java)

            Timer("Done", false).schedule(1000){
                startActivity(intent)
                finish()
            }
        }
    }
}
