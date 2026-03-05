package com.yourapp.moneyonred
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

import android.icu.util.Calendar

data class users(
    val userid: String = "",
    val name: String = "",
    val email: String = "",
    val passwordHash: String = "", 
    val tel: String = "",
    val birth: Calendar? = null,
    val address: String = "",
    val balance: Double = 0.0
)

data class goods(
    val userid: String = "",
    val goodsid: String = "",
    val name: String = "",
    val price: Double = 0.0
)
