package com.example.mc_assignment3.DB

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.mc_assignment3.SensorData
import java.io.File
import java.io.FileWriter

class MyDataSource(context: Context) {

    val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun insertData(time: Long, X: String, Y: String, Z: String) {
        val db = dbHelper.writableDatabase

        // Delete old data
        val tenSecondsAgo = System.currentTimeMillis() - 10_000
        val selection = "${DatabaseContract.Entry.COLUMN_NAME_Time} <= ?"
        val selectionArgs = arrayOf(tenSecondsAgo.toString())
        db.delete(DatabaseContract.Entry.TABLE_NAME, selection, selectionArgs)

        // Insert new data
        val values = ContentValues().apply {
            put(DatabaseContract.Entry.COLUMN_NAME_Time, time)
            put(DatabaseContract.Entry.COLUMN_NAME_X, X)
            put(DatabaseContract.Entry.COLUMN_NAME_Y, Y)
            put(DatabaseContract.Entry.COLUMN_NAME_Z, Z)
        }
        val newRowId = db.insert(DatabaseContract.Entry.TABLE_NAME, null, values)

        if (newRowId != -1L) {
            Log.d("MyDataSource", "Data inserted successfully with row ID: $newRowId")
        } else {
            Log.e("MyDataSource", "Failed to insert data")
        }
    }

    fun getRowCount(): Int {
        val db = dbHelper.readableDatabase

        val query = "SELECT COUNT(*) FROM ${DatabaseContract.Entry.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        var count = 0
        if (cursor != null) {
            cursor.moveToFirst()
            count = cursor.getInt(0)
            cursor.close()
        }

        return count
    }
    fun getAllData(): List<SensorData> {
        val db = dbHelper.readableDatabase
        val dataList = mutableListOf<SensorData>()

        val query = "SELECT * FROM ${DatabaseContract.Entry.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val time = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_Time))
                val x = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_X))
                val y = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_Y))
                val z = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_Z))

                val data = SensorData(time, x.toFloat(), y.toFloat(), z.toFloat())
                dataList.add(data)
            }
        }

        return dataList
    }

    fun exportDataToFile(context: Context, fileName: String) {
        val db = dbHelper.readableDatabase

        val query = "SELECT * FROM ${DatabaseContract.Entry.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        val externalDir = context.getExternalFilesDir(null)
        val file = File(externalDir, fileName)

        try {
            FileWriter(file).use { writer ->
                cursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val time = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_Time))
                        val x = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_X))
                        val y = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_Y))
                        val z = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_Z))

                        // Write data to the file
                        writer.write("$time, $x, $y, $z\n")
                    }
                }
            }
            Log.d("ExportDataToFile", "Data exported to file: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e("ExportDataToFile", "Error exporting data to file", e)
        }
    }

}