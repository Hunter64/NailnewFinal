package com.hector.nailnewfinal.activities.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hector.nailnewfinal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser





class SignUpActivity : AppCompatActivity() {

    //private val mAuth: FirebaseAuth? = null
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();


        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser === null){
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
            createAccount("lopezhector233@gmail.com", "123456789")
        }
        else{
            Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show()
        }
        //updateUI(currentUser)

    }

    private fun createAccount(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Create user with email -> Success", Toast.LENGTH_SHORT).show()
                    val user = mAuth.currentUser
                }
                else{
                    Toast.makeText(this, "Create user with email -> Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
