package com.yourapp.moneyonred

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Scaffold(
        bottomBar = { BankBottomNavigation(selectedItem = 1, onItemSelected = onItemSelected@{ index ->
            onNavigate(index)
        }) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                    BankActionButton(icon = Icons.Default.QrCodeScanner, label = "Qr รับเงิน")
                    BankActionButton(
                        icon = Icons.Default.AccountBalance, 
                        label = "โอนเงิน",
                        modifier = Modifier.clickable {
                            onNavigateToTransfer()
                        }
                    )
                }
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

        // Pop-up เตือนให้เข้าสู่ระบบ
        if (showLoginWarning) {
            AlertDialog(
                onDismissRequest = { showLoginWarning = false },
                title = { Text("แจ้งเตือน") },
                text = { Text("กรุณาเข้าสู่ระบบก่อนทำรายการเติมเงิน") },
                confirmButton = {
                    Button(onClick = { 
                        showLoginWarning = false
                        // TODO: นำทางไปหน้า Sign In
                    }) {
                        Text("ไปหน้าเข้าสู่ระบบ")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLoginWarning = false }) {
                        Text("ปิด")
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
