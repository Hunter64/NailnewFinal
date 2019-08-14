package com.hector.nailnewfinal.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.hector.nailnewfinal.R
import com.hector.nailnewfinal.activities.extensions.goToActivity
import com.hector.nailnewfinal.activities.extensions.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        buttonLogIn.setOnClickListener {
            //When is login this capture email and password and validate this
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if(isValidEmailAndPassword(email, password)){
                Log.w("----> ", "is email valid")
                logInByEmail(email, password)
            }
        }

        textViewForgotPassword.setOnClickListener{
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonCreateAccount.setOnClickListener {
            goToActivity<SignUpActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

    }

    private fun logInByEmail(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful)
                    toast("User is now logged in!")

                else
                    toast("An unexpected occurred, please try again")

            }
    }

    private fun isValidEmailAndPassword(email: String, password: String): Boolean{
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
}
