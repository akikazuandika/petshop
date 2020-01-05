package com.appneko.petshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_admin_dashboard.*

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val sharedPreference =  getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val username = sharedPreference.getString("username", null)
        val type = sharedPreference.getString("type", "type")
        if (username == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (type != "admin"){
            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.clear()
            editor.commit()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        adminDashboard_btn_to_category.setOnClickListener {
            var intent = Intent(this, CategoriesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        }

        adminDashboard_btn_to_product.setOnClickListener {
            var intent = Intent(this, ProductsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            finish()
        }

        adminDashboard_btn_logout.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.clear()
            editor.commit()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
