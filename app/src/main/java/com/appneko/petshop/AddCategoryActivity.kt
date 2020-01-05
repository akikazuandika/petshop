package com.appneko.petshop

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.appneko.petsho.CategoriesModel
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

            var query = "INSERT INTO ${CategoriesModel.TABLE_NAME}(name) VALUES('${name}'); SELECT * from SQLITE_SEQUENCE;"
            var result = db.rawQuery(query, null)
            if (result.moveToFirst()){
                if (result.getString(0).toString() != "-1"){
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
