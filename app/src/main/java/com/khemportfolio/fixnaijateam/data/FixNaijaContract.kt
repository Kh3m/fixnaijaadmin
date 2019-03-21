package com.khemportfolio.fixnaijateam.data

import android.net.Uri
import android.provider.BaseColumns

class FixNaijaContract {

    companion object {
        val FIXNAIJA_AUTHORITY = "com.khemportfolio.fixnaijateam.fixnaija"
        val BASE_FIXNAIJA_CONTENT_URI =  Uri.parse("content://${FIXNAIJA_AUTHORITY}")
        val USER_PATH = "users"
        val PROBLEM_PATH = "problems"
    }

    class UserEntry : BaseColumns {

        companion object {

            val USERS_CONTENT_URI = Uri.withAppendedPath(BASE_FIXNAIJA_CONTENT_URI, USER_PATH)


            val TABLE_NAME = "users"

            val _ID = BaseColumns._ID
//            val COLUMN_USER_EMAIL = "email"
//            val COLUMN_USER_PASSWORD = "password"
            val COLUMN_USER_TOKEN = "token"
        }
    }

    class ProblemEntry : BaseColumns {

        companion object {

            val PROBLEMS_CONTENT_URI = Uri.withAppendedPath(BASE_FIXNAIJA_CONTENT_URI, PROBLEM_PATH)
        }
    }
}