package com.yourapp.moneyonred.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.moneyonred.BankHeader
import com.yourapp.moneyonred.BalanceCard
import com.yourapp.moneyonred.BankActionButton
import com.yourapp.moneyonred.ui.theme.MONEYONREDTheme

@Composable
fun BankScreen(
    modifier: Modifier = Modifier,
    onNavigate: (Int) -> Unit = {},
    onNavigateToTransfer: () -> Unit = {}
) {
    var showTopUpDialog by remember { mutableStateOf(false) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showLoginWarning by remember { mutableStateOf(false) }
    var amount by remember { mutableStateOf("") }
    
    // จำลองสถานะการเข้าสู่ระบบ (ในอนาคตควรใช้ FirebaseAuth หรือ State ที่เก็บข้อมูลผู้ใช้)
    val isLoggedIn = false 

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.White, Color(0xFFFFF9C4))
                    )
                )
        ) {
            BankHeader()

            Spacer(modifier = Modifier.height(40.dp))

            BalanceCard()

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BankActionButton(
                    icon = Icons.Default.AddCircleOutline, 
                    label = "เติมเงิน",
                    modifier = Modifier.clickable {
                        if (isLoggedIn) {
                            showTopUpDialog = true
                        } else {
                            showLoginWarning = true
                        }
                    }
                )
                BankActionButton(
                    icon = Icons.Default.QrCodeScanner, 
                    label = "Qr รับเงิน",
                    modifier = Modifier.clickable {
                        if (isLoggedIn) {
                            // TODO: ไปหน้า QR
                        } else {
                            showLoginWarning = true
                        }
                    }
                )
                BankActionButton(
                    icon = Icons.Default.AccountBalance, 
                    label = "โอนเงิน",
                    modifier = Modifier.clickable {
                        // เปลี่ยนมาตรวจสอบการเข้าสู่ระบบที่นี่ตามที่ผู้ใช้ต้องการ
                        if (isLoggedIn) {
                            onNavigateToTransfer()
                        } else {
                            showLoginWarning = true
                        }
                    }
                )
            }
        }

        // Pop-up กรอกจำนวนเงิน
        if (showTopUpDialog) {
            AlertDialog(
                onDismissRequest = { showTopUpDialog = false },
                title = { Text("เติมเงิน") },
                text = {
                    Column {
                        Text("กรุณากรอกจำนวนเงินที่ต้องการเติม")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = amount,
                            onValueChange = { amount = it },
                            label = { Text("จำนวนเงิน") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { 
                        if (amount.isNotEmpty()) {
                            showTopUpDialog = false
                            showConfirmationDialog = true
                        }
                    }) {
                        Text("ถัดไป")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTopUpDialog = false }) {
                        Text("ยกเลิก")
                    }
                }
            )
        }

        // Pop-up ยืนยันการเติมเงิน
        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("ยืนยันการทำรายการ") },
                text = {
                    Column {
                        Text("คุณต้องการเติมเงินจำนวน")
                        Text(
                            text = "$amount $",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD600)
                        )
                        Text("ใช่หรือไม่?")
                    }
                },
                confirmButton = {
                    Button(onClick = { 
                        // TODO: จัดการการเติมเงินจริงที่นี่
                        showConfirmationDialog = false
                        amount = "" // ล้างค่าหลังจากทำรายการเสร็จ
                    }) {
                        Text("ยืนยัน")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmationDialog = false }) {
                        Text("แก้ไข")
                    }
                }
            )
        }

        // Pop-up เตือนให้เข้าสู่ระบบ (ปรับสไตล์ตามรูปภาพ)
        if (showLoginWarning) {
            AlertDialog(
                onDismissRequest = { showLoginWarning = false },
                title = { 
                    Text(
                        "แจ้งเตือน", 
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ) 
                },
                text = { 
                    Text(
                        "กรุณาเข้าสู่ระบบก่อนทำรายการ",
                        fontSize = 16.sp
                    ) 
                },
                confirmButton = {
                    Button(
                        onClick = { 
                            showLoginWarning = false
                            // TODO: นำทางไปหน้า Sign In
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4)), // สีม่วงตามรูป
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("ไปหน้าเข้าสู่ระบบ", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLoginWarning = false }) {
                        Text("ปิด", color = Color(0xFF6750A4))
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BankScreenPreview() {
    MONEYONREDTheme {
        BankScreen()
    }
}
