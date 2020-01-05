package com.appneko.petshop

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.appneko.petsho.UsersModel
import com.google.android.material.snackbar.Snackbar
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

            var cv = ContentValues()

            cv.put(UsersModel.NAME, name)
            cv.put(UsersModel.USERNAME, username)
            cv.put(UsersModel.PASSWORD, password)
            cv.put(UsersModel.TYPE, "user")

            var result = db.insert(UsersModel.TABLE_NAME, null, cv)

            if (result == (-1).toLong()){
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                var snackbar = Snackbar.make(it, "Failed", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)

                var snackbar = Snackbar.make(it, "Sucess Register", Snackbar.LENGTH_SHORT)
                snackbar.show()

                var intent = Intent(this@RegisterActivity, LoginActivity::class.java)

                Timer("Done", false).schedule(1000){
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
