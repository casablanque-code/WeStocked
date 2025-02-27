package com.example.westocked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddEquipmentDialog(
    prefilledInventoryNumber: Long?,
    onDismiss: () -> Unit,
    onSave: (Equipment) -> Unit
) {
    var inventoryNumberText by remember { mutableStateOf(prefilledInventoryNumber?.toString() ?: "") }
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var serialNumber by remember { mutableStateOf("") }
    var macAddress by remember { mutableStateOf("") }
    val inputTextStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
    val scrollState = rememberScrollState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            // Заголовок компактнее – меньший шрифт и в одну строку
            Text(text = "Добавить оборудование", fontSize = 12.sp)
        },
        text = {
            // Используем компактную Column (при необходимости можно добавить вертикальную прокрутку)
            Column(modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                //.padding(20.dp)
            ) {
                TextField(
                    value = inventoryNumberText,
                    onValueChange = { inventoryNumberText = it },
                    label = { Text("Инвентарный номер", fontSize = 8.sp) },
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.height(50.dp),
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название", fontSize = 8.sp) },
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.height(50.dp),
                    textStyle = inputTextStyle
                )
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Локация", fontSize = 8.sp) },
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.height(50.dp),
                    textStyle = inputTextStyle
                )
                TextField(
                    value = serialNumber,
                    onValueChange = { serialNumber = it },
                    label = { Text("Серийный номер", fontSize = 8.sp) },
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.height(50.dp),
                    textStyle = inputTextStyle
                )
                TextField(
                    value = macAddress,
                    onValueChange = { macAddress = it },
                    label = { Text("MAC адрес", fontSize = 8.sp) },
                    modifier = Modifier
                        .fillMaxWidth(),
                        //.height(50.dp),
                    textStyle = inputTextStyle
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val invNumber = inventoryNumberText.toLongOrNull() ?: 0L
                val newEquipment = Equipment(
                    inventory_number = invNumber,
                    name = name,
                    location = location,
                    serial_number = serialNumber,
                    mac_address = macAddress
                )
                onSave(newEquipment)
            }) {
                Text(text = "Добавить", fontSize = 9.sp)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Отмена", fontSize = 9.sp)
            }
        }
    )
}
