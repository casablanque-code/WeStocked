package com.example.westocked

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
    // Переменная для выделения найденной карточки (например, если дубликат найден)
    var highlightedEquipment by remember { mutableStateOf<Equipment?>(null) }
    // Переменная для управления показом экрана сканирования QR
    var showQrScanner by remember { mutableStateOf(false) }
    // Состояние для Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Состояние списка и scope для анимации прокрутки
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Если отсканирован номер – проверяем, есть ли он в списке
    LaunchedEffect(scannedInventoryNumber) {
        scannedInventoryNumber?.let { id ->
            val matchingEquipment = equipmentList.find { it.inventory_number == id }
            if (matchingEquipment != null) {
                highlightedEquipment = matchingEquipment
                // Определяем отфильтрованный список (тот же, что в UI)
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
                    // Прокручиваем к найденному элементу (подберите нужное смещение, здесь 0)
                    coroutineScope.launch {
                        listState.animateScrollToItem(index, scrollOffset = 0)
                    }
                }
                // Показываем сообщение о дубликате через Snackbar
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Оборудование с таким инвентарным номером уже существует")
                }
                delay(5000)
                highlightedEquipment = null


            scannedInventoryNumber = null
            } else {
                // Если номер не найден – открываем диалог добавления
                showAddDialog = true
            }
        }
    }

    // Фильтрация списка по всем полям
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
            // Заголовок с количеством элементов (уменьшённый шрифт)
            Text(
                text = "Оборудование (${filteredList.size} элементов)",
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp),
                modifier = Modifier.padding(vertical = 3.dp)
            )
            // Строка поиска (уменьшённая по вертикали)
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            // Список карточек оборудования
            LazyColumn(state = listState) {
                items(filteredList, key = { it.inventory_number }) { equipment ->
                    // Если карточка соответствует выделенному оборудованию – изменяем фон
                    val isHighlighted = highlightedEquipment?.inventory_number == equipment.inventory_number
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { selectedEquipment = equipment }
                            .background(
                                color = if (isHighlighted)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                else MaterialTheme.colorScheme.surface
                            )
                            .padding(16.dp)
                    ) {
                        Text(text = "ID: ${equipment.inventory_number}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Name: ${equipment.name}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Location: ${equipment.location}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Serial Number: ${equipment.serial_number}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Mac Address: ${equipment.mac_address}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        // Кнопка "Добавить"
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Добавить")
        }

        // Кнопка "Скан QR"
        FloatingActionButton(
            onClick = { /* При нажатии запускаем экран сканера QR */ showQrScanner = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.QrCodeScanner, contentDescription = "Скан QR")
        }

        // Кнопка "Наверх"
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

        // Snackbar для оповещений
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

    // Диалог редактирования оборудования
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

    // Диалог добавления нового оборудования с проверкой дубликата
    if (showAddDialog) {
        AddEquipmentDialog(
            prefilledInventoryNumber = scannedInventoryNumber,
            onDismiss = { showAddDialog = false
                scannedInventoryNumber = null},
            onSave = { newEquipment ->
                // Если оборудование с таким номером уже существует – показываем сообщение и подсвечиваем карточку
                val duplicate = equipmentList.find { it.inventory_number == newEquipment.inventory_number }
                if (duplicate != null) {
                    highlightedEquipment = duplicate
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Оборудование с таким инвентарным номером уже существует")
                        // Прокручиваем до дубликата
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
