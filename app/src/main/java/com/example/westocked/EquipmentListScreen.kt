package com.example.westocked

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun EquipmentListScreen(equipmentViewModel: EquipmentViewModel = viewModel()) {
    val equipmentList by equipmentViewModel.equipmentList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    // Состояние для выбранного оборудования (для редактирования)
    var selectedEquipment by remember { mutableStateOf<Equipment?>(null) }

    // Фильтрация по названию и инвентарному номеру (приводим его к строке)
    val filteredList = if (searchQuery.isBlank()) {
        equipmentList
    } else {
        equipmentList.filter { equipment ->
            equipment.name.contains(searchQuery, ignoreCase = true) ||
                    equipment.inventory_number.toString().contains(searchQuery)
        }
    }


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // Заголовок с количеством элементов
        Text(
            text = "Оборудование (${filteredList.size} элементов)",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Поле поиска
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Список оборудования
        LazyColumn {
            items(filteredList) { equipment ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { selectedEquipment = equipment }
                        .padding(16.dp)
                ) {
                    Text(text = "ID: ${equipment.inventory_number}")
                    Text(text = "Name: ${equipment.name}")
                    Text(text = "Location: ${equipment.location}")
                    Text(text = "Serial Number: ${equipment.serial_number}")
                    Text(text = "Mac Address: ${equipment.mac_address}")
                }
            }
        }
    }
    // Если выбрано оборудование, показываем диалог редактирования
    selectedEquipment?.let { equipment ->
        EditEquipmentDialog(
            equipment = equipment,
            onDismiss = { selectedEquipment = null },
            onSave = { updatedEquipment ->
                equipmentViewModel.updateEquipment(updatedEquipment)
                selectedEquipment = null
            }
        )
    }
}

@Composable
fun EditEquipmentDialog(
    equipment: Equipment,
    onDismiss: () -> Unit,
    onSave: (Equipment) -> Unit
) {
    // Локальные состояния для редактирования полей
    var name by remember { mutableStateOf(equipment.name) }
    var location by remember { mutableStateOf(equipment.location) }
    var serialNumber by remember { mutableStateOf(equipment.serial_number) }
    var macAddress by remember { mutableStateOf(equipment.mac_address) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Редактировать оборудование") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Локация") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = serialNumber,
                    onValueChange = { serialNumber = it },
                    label = { Text("Серийный номер") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = macAddress,
                    onValueChange = { macAddress = it },
                    label = { Text("MAC адрес") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                // Создаём обновлённую копию оборудования
                val updatedEquipment = equipment.copy(
                    name = name,
                    location = location,
                    serial_number = serialNumber,
                    mac_address = macAddress
                )
                onSave(updatedEquipment)
            }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
