package com.yourapp.moneyonred

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object FirestoreService {
    private const val USERS_COLLECTION = "users"
    private const val GOODS_COLLECTION = "goods"

    // Get a reference to the Firestore database
    private val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }

    /**
     * Adds or updates a user in the Firestore 'users' collection.
     * If a user with the same userid already exists, it will be overwritten.
     *
     * @param user The user object to add or update.
     * @return True if the operation was successful, false otherwise.
     */
    suspend fun saveUser(user: users): Boolean {
        return try {
            db.collection(USERS_COLLECTION).document(user.userid).set(user).await()
            true
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error saving user: ", e)
            false
        }
    }

    /**
     * Adds a new good to the Firestore 'goods' collection.
     * This will create a new document with an auto-generated ID.
     *
     * @param good The good object to add.
     * @return The ID of the newly created document, or null on failure.
     */
    suspend fun addGood(good: goods): String? {
        return try {
            val documentReference = db.collection(GOODS_COLLECTION).add(good).await()
            documentReference.id
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error adding good: ", e)
            null
        }
    }

    /**
     * Retrieves a user from the 'users' collection by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The 'users' object if found, null otherwise.
     */
    suspend fun getUser(userId: String): users? {
        return try {
            val document = db.collection(USERS_COLLECTION).document(userId).get().await()
            document.toObject(users::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error getting user: ", e)
            null
        }
    }
}
