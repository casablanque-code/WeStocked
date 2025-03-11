@file:Suppress("DEPRECATION")

package com.example.westocked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditEquipmentDialog(
    equipment: Equipment,
    onDismiss: () -> Unit,
    onSave: (Equipment) -> Unit,
    onDelete: () -> Unit // callback для удаления
) {
    var name by remember { mutableStateOf(equipment.name) }
    var location by remember { mutableStateOf(equipment.location) }
    var serialNumber by remember { mutableStateOf(equipment.serial_number) }
    var macAddress by remember { mutableStateOf(equipment.mac_address) }
    // Определим компактный стиль для полей ввода
    val inputTextStyle = LocalTextStyle.current.copy(fontSize = 10.sp)
    // Форма диалога – скруглённые углы
    val dialogShape = RoundedCornerShape(8.dp)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(dialogShape),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 4.dp
            ) {
                Text(
                    text = "Редактировать оборудование",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        text = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(dialogShape),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .padding(4.dp)
                ) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Название", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = inputTextStyle
                    )
                    TextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Локация", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = inputTextStyle
                    )
                    TextField(
                        value = serialNumber,
                        onValueChange = { serialNumber = it },
                        label = { Text("Серийный номер", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = inputTextStyle
                    )
                    TextField(
                        value = macAddress,
                        onValueChange = { macAddress = it },
                        label = { Text("MAC адрес", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = inputTextStyle
                    )
                }
            }
        },
        confirmButton = {
            // Объединяем кнопку "Удалить" и "Сохранить" в одну строку
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(dialogShape),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 4.dp
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                ) {
                    Button(onClick = onDelete) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Удалить")
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(onClick = {
                        onSave(
                            equipment.copy(
                                name = name,
                                location = location,
                                serial_number = serialNumber,
                                mac_address = macAddress
                            )
                        )
                    }) {
                        Text(text = "Сохранить", fontSize = 9.sp)
                    }
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Отмена", fontSize = 9.sp)
            }
        }
    )
}
