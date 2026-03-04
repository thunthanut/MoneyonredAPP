package com.yourapp.moneyonred

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yourapp.moneyonred.ui.theme.MONEYONREDTheme

@Composable
fun BankScreen(modifier: Modifier = Modifier, onNavigate: (Int) -> Unit = {}) {
    Scaffold(
        bottomBar = { BankBottomNavigation(selectedItem = 1, onItemSelected = onNavigate) }
    ) { paddingValues ->
        // ใช้ Surface คลุมเพื่อให้มั่นใจว่ามีพื้นหลังสีขาวถ้า Gradient ไม่ทำงาน
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
                            colors = listOf(Color.White, Color(0xFFFFF9C4)) // เปลี่ยนเป็นเหลืองอ่อนให้เข้ากับ Header
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
                    BankActionButton(icon = Icons.Default.AddCircleOutline, label = "เติมเงิน")
                    BankActionButton(icon = Icons.Default.QrCodeScanner, label = "Qr รับเงิน")
                    BankActionButton(icon = Icons.Default.AccountBalance, label = "โอนเงิน")
                }
            }
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
