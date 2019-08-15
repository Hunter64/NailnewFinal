package com.hector.nailnewfinal.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hector.nailnewfinal.R
import com.hector.nailnewfinal.activities.extensions.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val mGoogleAPiClient: GoogleApiClient by lazy { getGoogleApiClient() }

    private val rescueCodeGoogleSignIn = 99 // -> Any number, this is only a flag for rescue code and compare than request

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        buttonLogIn.setOnClickListener {
            //When is login this capture email and password and validate this
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if(isValidEmail(email) && isValidPassword(password))
                logInByEmail(email, password)
            else
                toast("Please make sure all the data is correct!")

        }

        textViewForgotPassword.setOnClickListener{
            goToActivity<ForgotPasswordActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonCreateAccount.setOnClickListener {
            goToActivity<SignUpActivity>()
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }

        buttonLogInGoogle.setOnClickListener {
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleAPiClient)
            startActivityForResult(signInIntent, rescueCodeGoogleSignIn)
        }

        editTextEmail.validate { editTextEmail.error = if(isValidEmail(it)) null else "Email isn´t valid!"}

        editTextPassword.validate { editTextPassword.error = if(isValidPassword(it)) null else "Password isn't valid!" }

    }

    private fun logInByEmail(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    if(mAuth.currentUser!!.isEmailVerified){
                        toast("User is now logged in!")
                    }
                    else{
                        toast("User must confirm email first!")
                    }
                }
                else{
                    toast("An unexpected occurred, please try again")
                }

            }
    }

    private fun getGoogleApiClient(): GoogleApiClient{
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return  GoogleApiClient.Builder(this)
            .enableAutoManage(this, this) //context and listener
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun loginByGoogleAccountIntoFirebase(googleAccount: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
        //Receive data credential from google
        mAuth.signInWithCredential(credential).addOnCompleteListener(this){
            toast("Sign in by Google!!")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == rescueCodeGoogleSignIn){
            // -> Come login with Google
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess){
                // -> Validation google is correct for login
                val account = result.signInAccount
                loginByGoogleAccountIntoFirebase(account!!)
            }
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Connection Failed!")
    }

}
