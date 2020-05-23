package me.alberto.googlesignin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import me.alberto.googlesignin.model.User
import me.alberto.googlesignin.viewmodel.MainViewModel
const val RC_SIGN_IN = 12
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSigninButton()
        initViewModel()
        initGoogleSigninClient()
    }

    private fun initGoogleSigninClient() {
        val googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(
            this,
            googleSignInOptions
        )
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(mainViewModel::class.java)
    }

    private fun initSigninButton() {
        google_sign_in_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleAccount = task.getResult(ApiException::class.java)
                if (googleAccount != null) {
                    getGoogleCredentials(googleAccount)
                }
            } catch (error: ApiException){
                Log.d("signIn", error.message as String)
            }
        }
    }

    private fun getGoogleCredentials(googleAccount: GoogleSignInAccount) {
        val googleTokenId = googleAccount.idToken
        val authCredentials = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleCredentials(authCredentials)
    }

    private fun signInWithGoogleCredentials(authCredentials: AuthCredential) {
        mainViewModel.singInIWithGoogle(authCredentials)
        mainViewModel.googleUser.observe(this, Observer { user ->

            user ?: return@Observer

            if (user.isNew){
                createUser(user)
            } else {
                gotoHomeActivity(user)
            }

        })

    }

    private fun gotoHomeActivity(user: User) {

    }

    private fun createUser(user: User) {

    }
}
