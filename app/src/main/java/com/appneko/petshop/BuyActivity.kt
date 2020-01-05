package com.appneko.petshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.appneko.petsho.OrdersModel
import kotlinx.android.synthetic.main.activity_buy.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule

class BuyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        var productId = intent.getStringExtra("product_id")
        var productName = intent.getStringExtra("product_name")
        var productCategory = intent.getStringExtra("product_category")
        var productPrice = intent.getStringExtra("product_price")

        val sharedPreference =  getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val username = sharedPreference.getString("username", "username")


        buy_product_name.text = "Nama Produk : $productName"
        buy_product_category.text = "Kategori Produk : $productCategory"
        buy_product_price.text = "Harga Produk : $productPrice"
        buy_product_total.text = "Total : $productPrice"

        buy_inp_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var amount = 1
                if (buy_inp_amount.text.isNotEmpty()){
                    buy_product_show_amount.text = "Jumlah : " + buy_inp_amount.text.toString()
                    amount = buy_inp_amount.text.toString().toInt()
                }
                buy_product_total.text = "Total : " + (productPrice.toInt() * amount).toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var amount = 1
                if (buy_inp_amount.text.isNotEmpty()){
                    buy_product_show_amount.text = "Jumlah : " + buy_inp_amount.text.toString()
                    amount = buy_inp_amount.text.toString().toInt()
                }
                buy_product_total.text = "Total : " + (productPrice.toInt() * amount).toString()
            }
        })

        buy_btn_buy.setOnClickListener {
            if (buy_inp_amount.text.isNullOrEmpty()){
                buy_inp_amount.error = "Masukkan Jumlah"
            }

            if (buy_inp_address.text.isNullOrEmpty()){
                buy_inp_address.error = "Masukkan Alamat Pengiriman"
            }

            var db = MyDatabaseOpenHelper(this).writableDatabase

            var sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
            var date = sdf.format(Date())

            var result = db.rawQuery("INSERT INTO ${OrdersModel.TABLE_NAME}" +
                    "(${OrdersModel.ID_PRODUCT}, " +
                    "${OrdersModel.USERNAME}, " +
                    "${OrdersModel.PRICE}, " +
                    "${OrdersModel.AMOUNT}, " +
                    "${OrdersModel.ADDRESS}, " +
                    "${OrdersModel.DATE}" +
                    ") " +
                    "VALUES('${productId}', " +
                    "'${username}', " +
                    "'${productPrice.toInt()}', " +
                    "'${buy_inp_amount.text.toString().toInt()}', " +
                    "'${buy_inp_address.text}', " +
                    "'${date}'" +
                    ") ", null)

            if (result.moveToFirst()){
                if (result.getString(0).toString() != "-1"){
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_LONG).show()
                    Timer("Done", false).schedule(1000){
                        val intent = Intent(this@BuyActivity, LoginActivity::class.java)
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