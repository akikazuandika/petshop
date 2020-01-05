package com.appneko.petshop

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.appneko.petsho.CategoriesModel
import com.appneko.petsho.OrdersModel
import com.appneko.petsho.ProductsModel
import com.appneko.petsho.UsersModel

class MyDatabaseOpenHelper(ctx : Context) : SQLiteOpenHelper(ctx, "PetShop", null, 1){
    override fun onCreate(db: SQLiteDatabase?) {

        //Create table users
        val createTableUsers = "CREATE TABLE ${UsersModel.TABLE_NAME} (" +
                "${UsersModel.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${UsersModel.USERNAME} VARCHAR(255)," +
                "${UsersModel.PASSWORD} VARCHAR(255)," +
                "${UsersModel.NAME} VARCHAR(255)," +
                "${UsersModel.TYPE} VARCHAR(255)" +
                ")"
        db?.execSQL(createTableUsers)

        //Create table products
        val createTableProducts = "CREATE TABLE ${ProductsModel.TABLE_NAME} (" +
                "${ProductsModel.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${ProductsModel.NAME} VARCHAR(255)," +
                "${ProductsModel.CATEGORY} VARCHAR(255)," +
                "${ProductsModel.PRICE} INTEGER (11)" +
                ")"
        db?.execSQL(createTableProducts)

        //Create table category
        val createTableCategory = "CREATE TABLE ${CategoriesModel.TABLE_NAME} (" +
                "${CategoriesModel.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${CategoriesModel.NAME} VARCHAR(255)" +
                ")"
        db?.execSQL(createTableCategory)

        //Create table category
        val createTableOrder = "CREATE TABLE ${OrdersModel.TABLE_NAME} (" +
                "${OrdersModel.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${OrdersModel.ID_PRODUCT} VARCHAR(255)," +
                "${OrdersModel.USERNAME} VARCHAR(255)," +
                "${OrdersModel.AMOUNT} INTEGER(11)," +
                "${OrdersModel.PRICE} INTEGER(11)," +
                "${OrdersModel.ADDRESS} VARCHAR(255)," +
                "${OrdersModel.STATUS} VARCHAR(255) DEFAULT '0'," +
                "${OrdersModel.DATE} VARCHAR(255)" +
                ")"
        db?.execSQL(createTableOrder)

        var cv = ContentValues()
        cv.put(UsersModel.USERNAME, "admin")
        cv.put(UsersModel.PASSWORD, "admin123")
        cv.put(UsersModel.NAME, "Administrator")
        cv.put(UsersModel.TYPE, "admin")
        db?.insert(UsersModel.TABLE_NAME, null, cv)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}