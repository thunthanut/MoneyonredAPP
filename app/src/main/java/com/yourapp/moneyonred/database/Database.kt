package com.yourapp.moneyonred.database

import java.util.Date

data class users(
    val userid: String = "",
    val name: String = "",
    val email: String = "",
    val passwordHash: String = "",
    val numaccount: String = "",
    val tel: String = "",
    val birth: String? = null,
    val address: String = "",
    val balance: Double = 0.0
)

data class goods(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val category: String = ""
)

data class pocket(
    val userid: String = "",
    val pocketid: String = "",
    val pocketname: String = "",
    val balance: Double = 0.0
)

data class transaction(
    val transactionId: String = "",
    val userid: String = "",
    val pocketid: String = "",
    val type: String = "EXPENSE", // EXPENSE หรือ INCOME
    val category: String = "", // Shopping, Food, Subscription, etc.
    val amount: Double = 0.0,
    val note: String = "",
    val timestamp: Long = Date().time
)

// เพิ่มคลาสสำหรับหมวดหมู่ (เผื่ออนาคตต้องการแยกไอคอน/สี)
data class category(
    val name: String = "",
    val icon: String = "",
    val color: String = ""
)
