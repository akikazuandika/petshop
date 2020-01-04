package com.appneko.petshop

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UserDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        val sharedPreference =  getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val username = sharedPreference.getString("username", "username")
        val type = sharedPreference.getString("type", "type")
        println("$username - $type")
    }
}
