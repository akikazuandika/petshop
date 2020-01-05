package com.appneko.petshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_buy.*

class BuyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        var productId = intent.getStringExtra("product_id")
        var productName = intent.getStringExtra("product_name")
        var productCategory = intent.getStringExtra("product_category")
        var productPrice = intent.getStringExtra("product_price")

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
    }
}