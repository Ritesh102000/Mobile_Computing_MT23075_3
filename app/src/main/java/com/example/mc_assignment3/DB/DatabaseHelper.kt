package com.example.mc_assignment3.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // If you need to add or modify tables, you'll handle it here.
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyDatabase.db"
        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DatabaseContract.Entry.TABLE_NAME}(" +
                    "${DatabaseContract.Entry._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContract.Entry.COLUMN_NAME_X} TEXT," +
                    "${DatabaseContract.Entry.COLUMN_NAME_Y} TEXT," +
                    "${DatabaseContract.Entry.COLUMN_NAME_Z} TEXT,"+
                    "${DatabaseContract.Entry.COLUMN_NAME_Time} TEXT)"
    }
}
