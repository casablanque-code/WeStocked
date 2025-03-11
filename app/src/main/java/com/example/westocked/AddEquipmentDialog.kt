import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.westocked.Equipment

@Composable
fun AddEquipmentDialog(
    prefilledInventoryNumber: Long?,
    onDismiss: () -> Unit,
    onSave: (Equipment) -> Unit
) {
    // Локальные состояния для полей ввода
    var inventoryNumberText = remember { mutableStateOf(prefilledInventoryNumber?.toString() ?: "") }
    var name = remember { mutableStateOf("") }
    var location = remember { mutableStateOf("") }
    var serialNumber = remember { mutableStateOf("") }
    var macAddress = remember { mutableStateOf("") }

    // Определим форму (скруглённые углы)
    val dialogShape: Shape = RoundedCornerShape(8.dp)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            // Заголовок с меньшим шрифтом
            Text(text = "Добавить оборудование", fontSize = 12.sp)
        },
        text = {
            // Оборачиваем содержимое в Surface для красивого фона и тени
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(dialogShape),
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(4.dp)) {
                    TextField(
                        value = inventoryNumberText.value,
                        onValueChange = { inventoryNumberText.value = it },
                        label = { Text("Инвентарный номер", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Название", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = location.value,
                        onValueChange = { location.value = it },
                        label = { Text("Локация", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = serialNumber.value,
                        onValueChange = { serialNumber.value = it },
                        label = { Text("Серийный номер", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = macAddress.value,
                        onValueChange = { macAddress.value = it },
                        label = { Text("MAC адрес", fontSize = 8.sp) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val invNumber = inventoryNumberText.value.toLongOrNull() ?: 0L
                // Создаем Equipment по введенным данным
                val newEquipment = Equipment(
                    inventory_number = invNumber,
                    name = name.value,
                    location = location.value,
                    serial_number = serialNumber.value,
                    mac_address = macAddress.value
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
