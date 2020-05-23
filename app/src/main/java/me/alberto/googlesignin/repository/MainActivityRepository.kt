package me.alberto.googlesignin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import me.alberto.googlesignin.model.User

class MainActivityRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()


    fun firebaseSignInWithGoogle(authCredentials: AuthCredential): MutableLiveData<User> {
        var authenticatedUser = MutableLiveData<User>()
        firebaseAuth.signInWithCredential(authCredentials).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                val isNewUser = task.result?.additionalUserInfo?.isNewUser!!
                val firebaseUser = firebaseAuth.currentUser
                if (firebaseUser != null){
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName!!
                    val email = firebaseUser.email!!
                    val user = User(uid, name, email, isNew = isNewUser)
                    authenticatedUser.value = user
                }
            } else {
                Log.d("FirebaseUser", task.exception?.message.toString())
            }
        }
        return authenticatedUser
    }
}