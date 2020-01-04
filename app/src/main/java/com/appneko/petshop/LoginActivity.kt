package com.appneko.petshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appneko.petsho.UsersModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.db.select

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn_login.setOnClickListener {

            val username = login_inp_username.text.toString()
            val password = login_inp_password.text.toString()
            val dbReadable = MyDatabaseOpenHelper(this).readableDatabase

            var result = dbReadable.rawQuery("SELECT * FROM ${UsersModel.TABLE_NAME} WHERE username='${username}'", null)

            if (result.count > 0){
                var user = Users()

                if (result.moveToFirst()){
                    do {
                        user.id = result.getString(0).toInt()
                        user.username = result.getString(1).toString()
                        user.password = result.getString(2).toString()
                        user.name = result.getString(3).toString()
                        user.type = result.getString(4).toString()
                    }while (result.moveToNext())
                }

                if(user.password == password){
                    val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                    var editor = sharedPreferences.edit()
                    editor.putString("username", username)
                    editor.putString("type", user.type)
                    editor.commit()
                    if (user.type == "user"){
                        val intent = Intent(this, UserDashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else if(user.type == "admin"){
                        val intent = Intent(this, AdminDashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}