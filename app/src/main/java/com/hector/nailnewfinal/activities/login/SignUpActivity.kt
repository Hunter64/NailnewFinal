package com.hector.nailnewfinal.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hector.nailnewfinal.R
import com.google.firebase.auth.FirebaseAuth
import com.hector.nailnewfinal.activities.extensions.goToActivity
import com.hector.nailnewfinal.activities.extensions.toast
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonGoLogIn.setOnClickListener {
            goToActivity<LoginActivity> {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //For exit when click back button android
            }
        }

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (isValidEmailAndPassword(email, password))
                signUpByEmail(email, password)
            else
                toast("Please fill all the data and confirm password is correct!")
        }
    }

    private fun signUpByEmail(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
                toast("An email has been sent to you. Please confirm before sign in.")
            else
                toast("An unexpected error occurred, please try again.")
        }
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean {
        return !email.isNullOrEmpty() &&
                !password.isNullOrEmpty() &&
                password === editTextConfirmPassword.text.toString()
    }
}
