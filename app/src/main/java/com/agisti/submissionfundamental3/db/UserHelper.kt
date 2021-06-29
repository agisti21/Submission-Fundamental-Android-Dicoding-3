package com.agisti.submissionfundamental3.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion.TABLE_NAME
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion.USERNAME
import com.agisti.submissionfundamental3.db.DatabaseContract.UserColums.Companion._ID
import java.sql.SQLException

class UserHelper (context: Context){
    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase


    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE : UserHelper? = null

        fun getInstance(context: Context) : UserHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun close(){
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC",
            null)
    }
    fun queryById(id: String): Cursor {
        return database.query(DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long{
        return database.insert(DATABASE_TABLE,null, values)
    }
    fun update(id: String, contentValues: ContentValues?) : Int{
        return database.update(DATABASE_TABLE, contentValues, "$_ID = ?", arrayOf(id))
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }

    fun check(username: String): Boolean {
        database = databaseHelper.writableDatabase
        val selectId =
            "SELECT * FROM $DATABASE_TABLE WHERE $USERNAME =?"
        val cursor =
            database.rawQuery(selectId, arrayOf(username))
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var i = 0
            while (cursor.moveToNext()) {
                i++
            }
            Log.d(Constraints.TAG, String.format("%d records found", i))
        }
        cursor.close()

        return check
    }
}