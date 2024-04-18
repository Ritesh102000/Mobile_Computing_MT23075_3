package com.example.mc_assignment3.DB

import android.provider.BaseColumns

object DatabaseContract {
    object Entry : BaseColumns {
        const val TABLE_NAME = "Orientation"
        const val COLUMN_NAME_Time = "time"
        const val COLUMN_NAME_X = "x"
        const val COLUMN_NAME_Y = "y"
        const val COLUMN_NAME_Z = "z"
        const val _ID = BaseColumns._ID
    }
}