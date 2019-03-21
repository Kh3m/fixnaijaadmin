package com.khemportfolio.fixnaijateam.navigation

import android.content.ContentValues
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.khemportfolio.fixnaijateam.R
import com.khemportfolio.fixnaijateam.api.FixNaijaApi
import com.khemportfolio.fixnaijateam.api.Parser
import com.khemportfolio.fixnaijateam.data.FixNaijaContract.ProblemEntry.Companion.PROBLEMS_CONTENT_URI
import com.khemportfolio.fixnaijateam.data.User
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject
import java.io.IOException
import com.khemportfolio.fixnaijateam.data.FixNaijaContract.UserEntry
import com.khemportfolio.fixnaijateam.data.FixNaijaContract.UserEntry.Companion.USERS_CONTENT_URI
import org.json.JSONException

class LoginFragment : Fragment() {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private var mAuthTask: UserLoginTask? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu( true )

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authLogin()
        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    private fun authLogin() {
        val projection = arrayOf("_id")
        val cursor = activity?.contentResolver?.query(USERS_CONTENT_URI, projection, null, null, null)
        if((cursor?.count as Int) >= 1) {
            Navigation.findNavController(email_sign_in_button).navigate(R.id.login_dest_to_landing_dest)
//            Navigation.findNavController(email_sign_in_button).popBackStack()
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)
            mAuthTask = UserLoginTask(emailStr, passwordStr)
            mAuthTask!!.execute("user/signin")
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }


    private fun showProgress(show: Boolean) {
        // The ViewPropertyAnimator APIs are not available, so simply show
        // and hide the relevant UI components.
        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_form.visibility = if (show) View.GONE else View.VISIBLE

    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) :
        AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String? {
            return try {
                FixNaijaApi.post(params[0], "{\"email\": \"$mEmail\", \"password\": \"$mPassword\"}")
            } catch (e: IOException) {
                e.toString()
            }
        }

        override fun onPostExecute(result: String?) {
            mAuthTask = null
            showProgress(false)

            if (result!!.isNotEmpty()) {
                Log.i("LF", result)
                try {
                    if(JSONObject(result).getJSONObject("result").getString("msg") == "Auth Successful") {
                        // store user details locally
                        insertUser(UserEntry.USERS_CONTENT_URI, Parser.parseUser(result))
                        Navigation.findNavController(email_sign_in_button).navigate(R.id.login_dest_to_landing_dest)
                    } else {
                        Snackbar.make(email_sign_in_button, "Auth Failed Try Again", Snackbar.LENGTH_LONG).show()
                    }
                } catch (exp: JSONException) {
                    Snackbar.make(email_sign_in_button, "Auth Failed Try Again", Snackbar.LENGTH_SHORT).show()
                }
            } else {
                password.error = getString(R.string.error_incorrect_password)
                password.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }

    private fun insertUser(uri: Uri, user: User): Uri? {
        val values = ContentValues()
        values.put(UserEntry._ID, user._id)
        values.put(UserEntry.COLUMN_USER_TOKEN, user.token)

        val projection = arrayOf("_id")
        val cursor = activity?.contentResolver?.query(Uri.parse("${UserEntry.USERS_CONTENT_URI}/${user._id}") , projection, null, null, null)
        if(cursor?.moveToNext() as Boolean) {
            return Uri.parse("${UserEntry.USERS_CONTENT_URI}/${user._id}")
        }
        return activity?.contentResolver?.insert(uri, values)
    }
}