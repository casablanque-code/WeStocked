package com.example.westocked

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun EquipmentListScreen(equipmentViewModel: EquipmentViewModel = viewModel()) {
    // Получаем список из StateFlow
    val equipmentListState = equipmentViewModel.equipmentList.collectAsState()
    val equipmentList = equipmentListState.value

    Column {
        Text(
            text = "Список оборудования (${equipmentList.size} элементов):",
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(equipmentList) { equipment ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "ID: ${equipment.inventory_number}")
                    Text(text = "Name: ${equipment.name}")
                    Text(text = "Location: ${equipment.location}")
                    Text(text = "Serial Number: ${equipment.serial_number}")
                    Text(text = "Mac address: ${equipment.mac_address}")
                }
            }
        }
    }
}
