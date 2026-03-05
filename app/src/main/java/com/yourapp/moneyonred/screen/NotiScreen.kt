package com.yourapp.moneyonred.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.moneyonred.ui.theme.MONEYONREDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotiScreen(onNavigate: (Int) -> Unit = {}) {
    // จำลองสถานะการเข้าสู่ระบบ
    var isLoggedIn by remember { mutableStateOf(false) }

    // รายการแจ้งเตือนที่แปรผันตามการใช้งาน (จำลอง)
    val notifications = listOf(
        NotificationItem(
            title = "โอนเงินสำเร็จ",
            message = "โอนเงินไปยัง นายสมชาย จำนวน 500.00 บาท",
            time = "14:30",
            icon = Icons.Default.SwapHoriz,
            iconColor = Color(0xFF2196F3)
        ),
        NotificationItem(
            title = "ช้อปปิ้ง",
            message = "ชำระเงินค่าสินค้าที่ 7-Eleven จำนวน 120.00 บาท",
            time = "12:15",
            icon = Icons.Default.ShoppingCart,
            iconColor = Color(0xFFFF9800)
        ),
        NotificationItem(
            title = "อาหารและเครื่องดื่ม",
            message = "ชำระค่าอาหารที่ GrabFood จำนวน 250.00 บาท",
            time = "11:00",
            icon = Icons.Default.Fastfood,
            iconColor = Color(0xFF4CAF50)
        ),
        NotificationItem(
            title = "เติมเงินสำเร็จ",
            message = "คุณได้เติมเงินเข้ากระเป๋าจำนวน 1,000.00 บาท",
            time = "09:00",
            icon = Icons.Default.AddCard,
            iconColor = Color(0xFFE91E63)
        ),
        NotificationItem(
            title = "ระบบ",
            message = "ยินดีต้อนรับเข้าสู่ MONEYONRED เริ่มต้นจัดการเงินของคุณได้เลย!",
            time = "Yesterday",
            icon = Icons.Default.Notifications,
            iconColor = Color(0xFFFFD600)
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("การแจ้งเตือน", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFD600)
                )
            )
        }
    ) { paddingValues ->
        if (!isLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "กรุณาเข้าสู่ระบบเพื่อดูการแจ้งเตือน",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { /* TODO: Navigate to Sign In */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Sign In")
                        }
                        OutlinedButton(
                            onClick = { /* TODO: Navigate to Sign Up */ },
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                            Text("Sign Up", color = Color.Black)
                        }
                    }
                    // ปุ่มลัดเพื่อทดสอบ (เอาออกได้ภายหลัง)
                    TextButton(onClick = { isLoggedIn = true }, modifier = Modifier.padding(top = 16.dp)) {
                        Text("Demo: คลิกเพื่อจำลองการ Login", color = Color.Gray)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF5F5F5)),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(notifications) { noti ->
                    NotificationCard(noti)
                }
            }
        }
    }
}

data class NotificationItem(
    val title: String,
    val message: String,
    val time: String,
    val icon: ImageVector,
    val iconColor: Color
)

@Composable
fun NotificationCard(noti: NotificationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = noti.iconColor.copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = noti.icon,
                        contentDescription = null,
                        tint = noti.iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        noti.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                    Text(
                        noti.time,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    noti.message,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotiScreenPreview() {
    MONEYONREDTheme {
        NotiScreen()
    }
}
