package me.alberto.googlesignin.model

import com.google.firebase.firestore.Exclude
import java.io.Serializable

data class User(
    val uid: String,
    val name: String,
    @SuppressWarnings("WeakerAccess")
    val email: String,
    @Exclude
    var isAuthenticated: Boolean = false,
    @Exclude
    var isNew: Boolean = false,
    @Exclude
    var isCreated: Boolean = false
): Serializable