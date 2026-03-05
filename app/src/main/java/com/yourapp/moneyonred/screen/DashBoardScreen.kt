package com.yourapp.moneyonred.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.moneyonred.ui.theme.MONEYONREDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    onNavigate: (Int) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    // จำลองสถานะการเข้าสู่ระบบ
    var isLoggedIn by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Financial Report", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFFDE7)
                )
            )
        },
        containerColor = Color(0xFFFFFDE7)
    ) { paddingValues ->
        if (!isLoggedIn) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "กรุณาเข้าสู่ระบบเพื่อดูสรุปค่าใช้จ่าย",
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
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Black)
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
            DashboardContent(paddingValues)
        }
    }
}

@Composable
fun DashboardContent(paddingValues: PaddingValues) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Expense, 1 = Income

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Month Selector
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color.Blue, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Month", fontSize = 14.sp)
            }
        }

        // Donut Chart
        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(180.dp)) {
                val strokeWidth = 30.dp.toPx()
                // Orange Section
                drawArc(
                    color = Color(0xFFFFB300),
                    startAngle = 100f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                // Purple Section
                drawArc(
                    color = Color(0xFF7C4DFF),
                    startAngle = 280f,
                    sweepAngle = 120f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                // Red Section
                drawArc(
                    color = Color(0xFFFF5252),
                    startAngle = 40f,
                    sweepAngle = 60f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
            Text(
                text = "₹9400.0",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Expense / Income Switcher
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFFF1F1F5)
        ) {
            Row {
                TabButton(
                    text = "Expense",
                    isSelected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.weight(1f)
                )
                TabButton(
                    text = "Income",
                    isSelected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Category List
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CategoryItem("Shopping", "- 5120", Color(0xFFFFB300), 0.7f)
            CategoryItem("Subscription", "- 1280", Color(0xFF7C4DFF), 0.4f)
            CategoryItem("Food", "- 532", Color(0xFFFF5252), 0.2f)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(24.dp),
        color = if (isSelected) Color(0xFFFF5252) else Color.Transparent
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = if (isSelected) Color.White else Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CategoryItem(title: String, amount: String, color: Color, progress: Float) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.height(32.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
            Text(
                text = amount,
                color = Color(0xFFFF5252),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = color,
            trackColor = Color(0xFFF1F1F5),
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashBoardScreenPreview() {
    MONEYONREDTheme {
        DashBoardScreen()
    }
}
