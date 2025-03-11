package com.example.westocked

import AddEquipmentDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EquipmentListScreen(equipmentViewModel: EquipmentViewModel = viewModel()) {
    val equipmentList by equipmentViewModel.equipmentList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedEquipment by remember { mutableStateOf<Equipment?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var scannedInventoryNumber by remember { mutableStateOf<Long?>(null) }
    var highlightedEquipment by remember { mutableStateOf<Equipment?>(null) }
    var showQrScanner by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(scannedInventoryNumber) {
        scannedInventoryNumber?.let { id ->
            val matchingEquipment = equipmentList.find { it.inventory_number == id }
            if (matchingEquipment != null) {
                highlightedEquipment = matchingEquipment
                val filtered = if (searchQuery.isBlank()) {
                    equipmentList
                } else {
                    equipmentList.filter { equipment ->
                        equipment.name.contains(searchQuery, ignoreCase = true) ||
                                equipment.inventory_number.toString().contains(searchQuery) ||
                                equipment.location.contains(searchQuery, ignoreCase = true) ||
                                equipment.serial_number.contains(searchQuery, ignoreCase = true) ||
                                equipment.mac_address.contains(searchQuery, ignoreCase = true)
                    }
                }
                val index = filtered.indexOfFirst { it.inventory_number == matchingEquipment.inventory_number }
                if (index != -1) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index, scrollOffset = 0)
                    }
                }
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Оборудование с таким инвентарным номером уже существует")
                }
                delay(5000)
                highlightedEquipment = null
                scannedInventoryNumber = null
            } else {
                scannedInventoryNumber = id
                showAddDialog = true
            }
        }
    }

    val filteredList = if (searchQuery.isBlank()) {
        equipmentList
    } else {
        equipmentList.filter { equipment ->
            equipment.name.contains(searchQuery, ignoreCase = true) ||
                    equipment.inventory_number.toString().contains(searchQuery) ||
                    equipment.location.contains(searchQuery, ignoreCase = true) ||
                    equipment.serial_number.contains(searchQuery, ignoreCase = true) ||
                    equipment.mac_address.contains(searchQuery, ignoreCase = true)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = "Оборудование (${filteredList.size} шт.)",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp),
                modifier = Modifier.padding(vertical = 3.dp)
            )
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            LazyColumn(state = listState) {
                items(filteredList, key = { it.inventory_number }) { equipment ->
                    val isHighlighted = highlightedEquipment?.inventory_number == equipment.inventory_number
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)  // уменьшено расстояние между карточками
                            .clickable { selectedEquipment = equipment }
                            .background(
                                color = if (isHighlighted)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surface
                            )
                            .padding(8.dp)  // уменьшены внутренние отступы
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "ID: ${equipment.inventory_number}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Name: ${equipment.name}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Location: ${equipment.location}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Serial Number: ${equipment.serial_number}", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Mac Address: ${equipment.mac_address}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
        }

        FloatingActionButton(
            onClick = { showQrScanner = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.QrCodeScanner, contentDescription = "Скан QR")
        }

        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 80.dp)
        ) {
            Text("Up")
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    // Если экран сканера включён, отображаем его поверх
    if (showQrScanner) {
        QrCodeScannerScreen(
            onResult = { number ->
                if (number != null) scannedInventoryNumber = number
                showQrScanner = false
            },
            onCancel = { showQrScanner = false }
        )
    }

    selectedEquipment?.let { equipment ->
        EditEquipmentDialog(
            equipment = equipment,
            onDismiss = { selectedEquipment = null },
            onSave = { updatedEquipment ->
                equipmentViewModel.updateEquipment(updatedEquipment)
                selectedEquipment = null
            },
            onDelete = {
                equipmentViewModel.deleteEquipment(equipment)
                selectedEquipment = null
            }
        )
    }

    if (showAddDialog) {
        AddEquipmentDialog(
            prefilledInventoryNumber = scannedInventoryNumber,
            onDismiss = {
                showAddDialog = false
                scannedInventoryNumber = null
            },
            onSave = { newEquipment ->
                val duplicate = equipmentList.find { it.inventory_number == newEquipment.inventory_number }
                if (duplicate != null) {
                    highlightedEquipment = duplicate
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Оборудование с таким инвентарным номером уже существует")
                        val filtered = if (searchQuery.isBlank()) equipmentList else equipmentList.filter { equipment ->
                            equipment.name.contains(searchQuery, ignoreCase = true) ||
                                    equipment.inventory_number.toString().contains(searchQuery) ||
                                    equipment.location.contains(searchQuery, ignoreCase = true) ||
                                    equipment.serial_number.contains(searchQuery, ignoreCase = true) ||
                                    equipment.mac_address.contains(searchQuery, ignoreCase = true)
                        }
                        val index = filtered.indexOfFirst { it.inventory_number == duplicate.inventory_number }
                        if (index != -1) {
                            listState.animateScrollToItem(index, scrollOffset = 0)
                        }
                        delay(5000)
                        highlightedEquipment = null
                    }
                } else {
                    equipmentViewModel.addEquipment(newEquipment)
                }
                showAddDialog = false
                scannedInventoryNumber = null
            }
        )
    }
}
