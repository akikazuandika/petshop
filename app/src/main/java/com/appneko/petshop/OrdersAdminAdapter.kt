package com.appneko.petshop

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.OrdersModel
import com.appneko.petsho.ProductsModel
import com.google.android.material.snackbar.Snackbar

class OrdersAdminAdapter(private val orders : ArrayList<Orders>) : RecyclerView.Adapter<OrdersAdminAdapter.ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_order_admin, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val order = orders[position]

        var status = ""
        if (order.status == "1"){
            status = "Disetujui"
            holder.btnApprove.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
        }else if(order.status == "2"){
            status = "Ditolak"
            holder.btnApprove.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
        }else{
            status = "Menunggu"
        }

        holder.id.text = "ID : #" + order.id.toString()
        holder.name.text = "Nama Produk : " + order.productName
        holder.category.text = "Kategori Produk : " + order.productCategory
        holder.price.text = "Harga Produk : " + order.price.toString()
        holder.amount.text = "Jumlah : " + order.amount.toString()
        holder.total.text = "Total : " + (order.price * order.amount).toString()
        holder.address.text = "Alamat Pengiriman : " + order.address
        holder.username.text = "Penerima : " + order.username
        holder.status.text = "Status : " + status

        holder.btnApprove.setOnClickListener {
            var db = MyDatabaseOpenHelper(holder.itemView.context).writableDatabase

            var cv = ContentValues()
            cv.put(OrdersModel.STATUS, "1")
            db.update(OrdersModel.TABLE_NAME, cv, OrdersModel.ID + "=?", arrayOf(order.id.toString()))

            Snackbar.make(holder.itemView.rootView, "Success Approve", Snackbar.LENGTH_SHORT).show()
            holder.btnApprove.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
            holder.status.text = "Status : Disetujui"
        }

        holder.btnReject.setOnClickListener {
            var db = MyDatabaseOpenHelper(holder.itemView.context).writableDatabase

            var cv = ContentValues()
            cv.put(OrdersModel.STATUS, "2")
            db.update(OrdersModel.TABLE_NAME, cv, OrdersModel.ID + "=?", arrayOf(order.id.toString()))

            Snackbar.make(holder.itemView.rootView, "Success Reject", Snackbar.LENGTH_SHORT).show()
            holder.btnApprove.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
            holder.status.text = "Status : Ditolak"
        }
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id : TextView = itemView.findViewById(R.id.item_order_admin_id)
        var username : TextView = itemView.findViewById(R.id.item_order_admin_username)
        var name : TextView = itemView.findViewById(R.id.item_order_admin_product_name)
        var price : TextView = itemView.findViewById(R.id.item_order_admin_product_price)
        var category : TextView = itemView.findViewById(R.id.item_order_admin_product_category)
        var amount : TextView = itemView.findViewById(R.id.item_order_admin_amount)
        var total : TextView = itemView.findViewById(R.id.item_order_admin_total)
        var address : TextView = itemView.findViewById(R.id.item_order_admin_address)
        var status : TextView = itemView.findViewById(R.id.item_order_admin_status)
        var btnApprove : Button = itemView.findViewById(R.id.item_order_admin_btn_confirm)
        var btnReject : Button = itemView.findViewById(R.id.item_order_admin_btn_reject)
    }
}