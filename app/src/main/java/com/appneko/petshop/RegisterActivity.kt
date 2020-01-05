package com.appneko.petshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appneko.petsho.UsersModel
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import kotlin.concurrent.schedule

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_btn_register.setOnClickListener {
            var username = register_inp_username.text.toString()
            var password = register_inp_password.text.toString()
            var name = register_inp_name.text.toString()
            val db = MyDatabaseOpenHelper(this).writableDatabase

            var query = "INSERT INTO ${UsersModel.TABLE_NAME}(${UsersModel.USERNAME}, ${UsersModel.PASSWORD}, ${UsersModel.NAME}, ${UsersModel.TYPE}) VALUES('${username}', '${password}', '${name}', 'user')"
            var result = db.rawQuery(query, null)
            if (result.moveToFirst()){
                if (result.getString(0).toString() != "-1"){
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show()
                    Timer("Done", false).schedule(1000){
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }else{
                    Toast.makeText(this, "Gagal", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
