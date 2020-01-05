package com.appneko.petshop

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.appneko.petsho.CategoriesModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_category.*
import java.util.*
import kotlin.concurrent.schedule

class AddCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        addCategory_btn_add.setOnClickListener {
            var name = addCategory_inp_name.text.toString()

            var db = MyDatabaseOpenHelper(this).writableDatabase

            var cv = ContentValues()
            cv.put(CategoriesModel.NAME, name)

            var result = db.insert(CategoriesModel.TABLE_NAME, null, cv)

            if (result == (-1).toLong()){
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                var snackbar = Snackbar.make(it, "Failed", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                var snackbar = Snackbar.make(it, "Sucess", Snackbar.LENGTH_SHORT)
                snackbar.show()

                var intent = Intent(this@AddCategoryActivity, CategoriesActivity::class.java)

                Timer("Done", false).schedule(1000){
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
