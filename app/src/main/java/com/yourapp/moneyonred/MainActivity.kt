package com.yourapp.moneyonred

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // บังคับให้เป็น Light Theme และปิดสีอัตโนมัติจากระบบเพื่อแก้ปัญหาจอดำ
            MONEYONREDTheme(darkTheme = false, dynamicColor = false) {
                var selectedItem by remember { mutableIntStateOf(1) }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White // มั่นใจว่าพื้นหลังเป็นสีขาว
                ) {
                    when (selectedItem) {
                        0 -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { 
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Home Screen")
                                Button(onClick = { selectedItem = 1 }) { Text("Go to Bank") }
                            }
                        }
                        1 -> BankScreen(onNavigate = { selectedItem = it })
                        2 -> QrCodeScreen(onNavigate = { selectedItem = it })
                        3 -> NotiScreen(onNavigate = { selectedItem = it })
                        4 -> ProfileScreen(onNavigate = { selectedItem = it })
                    }
                }
            }
        }
    }
}


@Composable
fun BankHeader() {
    Surface(
        color = Color(0xFFFFD600),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MONEYONRED",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* TODO: Navigate to Sign In */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Sign In", fontSize = 12.sp, color = Color.White)
                }
                
                OutlinedButton(
                    onClick = { /* TODO: Navigate to Sign Up */ },
                    border = BorderStroke(1.dp, Color.Black),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("Sign Up", fontSize = 12.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "เงินทั้งหมด",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "--- $",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 36.sp,
                color = Color(0xFFFFD600)
            )
        }
    }
}

@Composable
fun BankActionButton(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .size(80.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    modifier = Modifier.size(36.dp),
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun BankBottomNavigation(selectedItem: Int = 1, onItemSelected: (Int) -> Unit = {}) {
    NavigationBar(
        containerColor = Color(0xFFFFD600),
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = selectedItem == 0,
            onClick = { onItemSelected(0) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = selectedItem == 1,
            onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Default.Wallet, contentDescription = "Bank") },
            label = { Text("Bank", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = selectedItem == 2,
            onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan") },
            label = { Text("Scan", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = selectedItem == 3,
            onClick = { onItemSelected(3) },
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification") },
            label = { Text("Notification", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black
            )
        )
        NavigationBarItem(
            selected = selectedItem == 4,
            onClick = { onItemSelected(4) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                selectedTextColor = Color.Red,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Black,
                unselectedTextColor = Color.Black
            )
        )
    }
}

@Composable
fun ScannerCorners() {
    Box(modifier = Modifier.size(240.dp)) {
        val color = Color.White
        val thickness = 4.dp
        val length = 40.dp

        // Top Left
        Box(Modifier.align(Alignment.TopStart).size(length, thickness).background(color))
        Box(Modifier.align(Alignment.TopStart).size(thickness, length).background(color))

        // Top Right
        Box(Modifier.align(Alignment.TopEnd).size(length, thickness).background(color))
        Box(Modifier.align(Alignment.TopEnd).size(thickness, length).background(color))

        // Bottom Left
        Box(Modifier.align(Alignment.BottomStart).size(length, thickness).background(color))
        Box(Modifier.align(Alignment.BottomStart).size(thickness, length).background(color))

        // Bottom Right
        Box(Modifier.align(Alignment.BottomEnd).size(length, thickness).background(color))
        Box(Modifier.align(Alignment.BottomEnd).size(thickness, length).background(color))
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MONEYONREDTheme {
        BankScreen()
    }
}