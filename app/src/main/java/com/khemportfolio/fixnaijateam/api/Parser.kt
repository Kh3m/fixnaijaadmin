package com.khemportfolio.fixnaijateam.api

import com.khemportfolio.fixnaijateam.data.User
import org.json.JSONObject

class Parser {

    companion object {
        fun parseUser(json: String) : User {

            val data = JSONObject(json)
            val user = data.getJSONObject("user")
            val token = data.getString("token")
            return User(user.getString("_id"), token)
        }
    }
}