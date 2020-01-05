package com.appneko.petshop

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.appneko.petsho.CategoriesModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_edit_category.*
import java.util.*
import kotlin.concurrent.schedule

class EditCategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_category)

        var id = intent.getStringExtra("id")
        var name = intent.getStringExtra("name")

        editCategory_inp_name.setText(name.toString())

        editCategory_btn_edit.setOnClickListener {
            var db = MyDatabaseOpenHelper(this).writableDatabase

            var inputName = editCategory_inp_name.text.toString()

            var cv = ContentValues()
            cv.put(CategoriesModel.NAME, inputName)

            db.update(CategoriesModel.TABLE_NAME, cv, CategoriesModel.ID + "=?", arrayOf(id))

            val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

            var snackbar = Snackbar.make(it, "Sucess", Snackbar.LENGTH_SHORT)
            snackbar.show()

            var intent = Intent(this@EditCategoryActivity, CategoriesActivity::class.java)

            Timer("Done", false).schedule(1000){
                startActivity(intent)
                finish()
            }
        }

    }
}
