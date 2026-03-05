package com.yourapp.moneyonred.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourapp.moneyonred.ui.theme.MONEYONREDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(onNavigateBack: () -> Unit = {}) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("ธนาคาร", "PromptPay", "บัญชี Moneyonred")
    
    // สถานะการเข้าสู่ระบบและ Pop-up แจ้งเตือน
    var showLoginWarning by remember { mutableStateOf(false) }
    val isLoggedIn = false // จำลองว่ายังไม่ได้เข้าสู่ระบบ

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("โอนเงิน", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFD600)
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color.Red,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color.Red
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontSize = 14.sp) }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val onCheckClick = {
                    if (!isLoggedIn) {
                        showLoginWarning = true
                    } else {
                        // ดำเนินการตรวจสอบข้อมูลจริงที่นี่
                    }
                }

                when (selectedTab) {
                    0 -> BankTransferSection(onCheckClick = onCheckClick)
                    1 -> PromptPayTransferSection(onCheckClick = onCheckClick)
                    2 -> AppAccountTransferSection(onCheckClick = onCheckClick)
                }
            }
        }

        // Pop-up เตือนให้เข้าสู่ระบบ
        if (showLoginWarning) {
            AlertDialog(
                onDismissRequest = { showLoginWarning = false },
                title = { Text("แจ้งเตือน") },
                text = { Text("กรุณาเข้าสู่ระบบก่อนทำรายการโอนเงิน") },
                confirmButton = {
                    Button(onClick = { 
                        showLoginWarning = false
                        // TODO: นำทางไปหน้าเข้าสู่ระบบ
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BankTransferSection(onCheckClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val banks = listOf("กสิกรไทย (KBank)", "ไทยพาณิชย์ (SCB)", "กรุงเทพ (BBL)", "กรุงไทย (KTB)", "กรุงศรี (BAY)")
    var selectedBank by remember { mutableStateOf(banks[0]) }
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("โอนเงินไปที่ธนาคาร", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedBank,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("เลือกธนาคาร") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    banks.forEach { bank ->
                        DropdownMenuItem(
                            text = { Text(bank) },
                            onClick = {
                                selectedBank = bank
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                label = { Text("เลขบัญชีธนาคาร") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("จำนวนเงิน") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCheckClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("ตรวจสอบข้อมูล", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PromptPayTransferSection(onCheckClick: () -> Unit) {
    var promptPayId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("โอนเงิน PromptPay", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = promptPayId,
                onValueChange = { promptPayId = it },
                label = { Text("หมายเลขพร้อมเพย์ (เบอร์โทร/เลขบัตรประชาชน)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("จำนวนเงิน") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCheckClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("ตรวจสอบข้อมูล", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AppAccountTransferSection(onCheckClick: () -> Unit) {
    var appAccountId by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("โอนเงินไปอีกบัญชี (ในแอพ)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = appAccountId,
                onValueChange = { appAccountId = it },
                label = { Text("เลขบัญชีของแอพ") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("จำนวนเงิน") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCheckClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("ตรวจสอบข้อมูล", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransferScreenPreview() {
    MONEYONREDTheme {
        TransferScreen()
    }
}
