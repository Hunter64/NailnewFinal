package com.hector.nailnewfinal.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.hector.nailnewfinal.R
import com.google.firebase.auth.FirebaseAuth
import com.hector.nailnewfinal.activities.extensions.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Start Activity Intent
        buttonGoLogIn.setOnClickListener {
            goToActivity<LoginActivity> {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //For exit when click back button android
            }
            //this use animation in and out like integer
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidEmailAndPassword(email, password))
                signUpByEmail(email, password)
            else
                toast("Please fill all the data and confirm password is correct!")
        }

        editTextEmail.validate { editTextEmail.error = if(isValidEmail(it)) null else "Email isn't valid!" }

        editTextPassword.validate { editTextPassword.error = if(isValidPassword(it)) null else "Password should contain 1 lower case, 1 uppercase, 1 number, 1 special character and 4 character length as least." }

        editTextConfirmPassword.validate { editTextConfirmPassword.error = if(isValidConfirmPassword(editTextPassword.text.toString(), it)) null else "Confirm password doesn't match with password" }
    }

    private fun signUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                toast("An email has been sent to you. Please confirm before sign in.")
                goToActivity<LoginActivity>{
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                toast("An unexpected error occurred, please try again.")
            }
        }
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean {
        return !email.isNullOrEmpty() &&
                !password.isNullOrEmpty() &&
                password === editTextConfirmPassword.text.toString()
    }
}
