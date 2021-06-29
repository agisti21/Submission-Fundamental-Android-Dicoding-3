package com.agisti.submissionfundamental3.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion.TABLE_NAME

internal class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private const val DATABASE_NAME = "db_user_fav"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                "(${DatabaseContract.UserColums._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${DatabaseContract.UserColums.AVATAR_URL} TEXT,"+
                "${DatabaseContract.UserColums.USERNAME} TEXT)"
 }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}
