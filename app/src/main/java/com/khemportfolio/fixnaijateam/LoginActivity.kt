package com.khemportfolio.fixnaijateam

import androidx.appcompat.app.AppCompatActivity
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navController = findNavController(R.id.main_nav_host)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item!!.onNavDestinationSelected(findNavController(R.id.main_nav_host)) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.main_nav_host).navigateUp()
}
