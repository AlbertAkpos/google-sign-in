package me.alberto.googlesignin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import me.alberto.googlesignin.model.User
import me.alberto.googlesignin.repository.MainActivityRepository

class MainViewModel(private val mainActivityRepository: MainActivityRepository): ViewModel() {

    private var _googleUser = MutableLiveData<User>()
    val googleUser: LiveData<User>
        get() = _googleUser

    fun singInIWithGoogle(authCredentials: AuthCredential) {
        _googleUser = mainActivityRepository.firebaseSignInWithGoogle(authCredentials)
    }
}