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

//class MyDatabaseOpenHelper private constructor(ctx: Context) :
//    ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
//    init {
//        instance = this
//    }
//
//    companion object {
//        private var instance: MyDatabaseOpenHelper? = null
//
//        @Synchronized
//        fun getInstance(ctx: Context) : MyDatabaseOpenHelper{
//            if (instance == null){
//                instance = MyDatabaseOpenHelper(ctx.applicationContext)
//            }
//            return instance as MyDatabaseOpenHelper
//        }
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {
//        // Here you create tables
//        db.createTable(
//            "Users", true,
//            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//            "username" to TEXT,
//            "password" to TEXT,
//            "name" to TEXT
//        )
//
//        db.createTable(
//            "Category", true,
//            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//            "name" to TEXT
//        )
//
//        db.createTable(
//            "Products", true,
//            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//            "name" to TEXT,
//            "category" to TEXT
//        )
//
//        db.createTable(
//            "Order", true,
//            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
//            "username" to TEXT,
//            "id_product" to TEXT,
//            "date" to TEXT
//        )
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        // Here you can upgrade tables, as usual
//        db.dropTable("Users", true)
//        db.dropTable("Category", true)
//        db.dropTable("Products", true)
//        db.dropTable("Order", true)
//    }
//}
//
//// Access property for Context
//val Context.database: MyDatabaseOpenHelper
//    get() = MyDatabaseOpenHelper.getInstance(applicationContext)