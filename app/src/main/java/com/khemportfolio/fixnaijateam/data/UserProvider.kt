package com.khemportfolio.fixnaijateam.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

import java.lang.IllegalArgumentException

class UserProvider : ContentProvider() {
    private val USERS = 100
    private val USER_ID = 101

    private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        mUriMatcher.addURI(FixNaijaContract.FIXNAIJA_AUTHORITY, FixNaijaContract.USER_PATH, USERS)
        mUriMatcher.addURI(FixNaijaContract.FIXNAIJA_AUTHORITY, "${FixNaijaContract.USER_PATH}/*", USER_ID)
    }

    lateinit var  fixNaijaDbHelper: FixNaijaDbHelper

    override fun onCreate(): Boolean {
        fixNaijaDbHelper = FixNaijaDbHelper(context)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, orderBy: String?): Cursor? {
        var cursor: Cursor? = null
        val db = fixNaijaDbHelper.readableDatabase

        when(mUriMatcher.match(uri)) {
            USERS -> {
               cursor = db.query("users", projection, null, null, null, null, null)
            }

            USER_ID -> {
                val selection = "_id = ?;"
                val selectionArgs = arrayOf(uri.toString().split("/")[uri.toString().split("/").size - 1])
                cursor = db.query("users", projection, selection, selectionArgs, null, null, orderBy)
            }

            else -> IllegalArgumentException("No uri match for query")
        }

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = fixNaijaDbHelper.writableDatabase

        when(mUriMatcher.match(uri)) {
            USERS -> return ContentUris.withAppendedId(uri, db.insert("users", null, values))
            else -> throw IllegalArgumentException("No uri matched for insert")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 1
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}