package com.example.westocked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
fun EditEquipmentDialog(
    equipment: Equipment,
    onDismiss: () -> Unit,
    onSave: (Equipment) -> Unit
) {
    var name by remember { mutableStateOf(equipment.name) }
    var location by remember { mutableStateOf(equipment.location) }
    var serialNumber by remember { mutableStateOf(equipment.serial_number) }
    var macAddress by remember { mutableStateOf(equipment.mac_address) }
    val inputTextStyle = LocalTextStyle.current.copy(fontSize = 10.sp)

    AlertDialog(
        onDismissRequest = onDismiss,
title = {
            Text(text = "Редактировать оборудование", fontSize = 12.sp)
        },

        text = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                //.padding(20.dp)
            ) {
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
                onSave(equipment.copy(name = name, location = location, serial_number = serialNumber, mac_address = macAddress))
            }) {
                Text(text = "Сохранить", fontSize = 9.sp)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Отмена", fontSize = 9.sp)
            }
        }
    )
}

