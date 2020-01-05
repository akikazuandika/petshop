package com.appneko.petshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_dashboard.*

class UserDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        val sharedPreference =  getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val username = sharedPreference.getString("username", "username")
        val type = sharedPreference.getString("type", "type")

        userDashboard_btn_to_product.setOnClickListener {
            var intent = Intent(this, ProductsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }

        userDashboard_btn_to_orders.setOnClickListener {
            var intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }

        userDashboard_btn_logout.setOnClickListener {
            var editor = sharedPreference.edit()
            editor.clear()
            editor.commit()
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
