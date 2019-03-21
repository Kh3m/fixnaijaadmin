package com.khemportfolio.fixnaijateam.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.khemportfolio.fixnaijateam.data.FixNaijaContract.UserEntry

class FixNaijaDbHelper(c: Context): SQLiteOpenHelper(c, "fixnaija.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${UserEntry.TABLE_NAME} " +
                "( ${UserEntry._ID} TEXT NOT NULL PRIMARY KEY, " +
                "${UserEntry.COLUMN_USER_TOKEN} TEXT NOT NULL ); " )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${UserEntry.TABLE_NAME} ")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

}