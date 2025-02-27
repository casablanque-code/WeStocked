/*
package com.example.westocked

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("DEPRECATION")
@Composable
fun ZXingQrCodeScannerScreen(
    onResult: (String?) -> Unit,
    onCancel: () -> Unit
) {
    val activity = (LocalContext.current as? ComponentActivity)
    // Лаунчер для получения результата от Intent сканера
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == ComponentActivity.RESULT_OK) {
            val intent = result.data
            // Используем встроенную функцию ZXing для разбора результата
            val scanResult = IntentIntegrator.parseActivityResult(result.resultCode, intent)
            onResult(scanResult?.contents)
        } else {
            onCancel()
        }
    }

    // Запускаем Intent для сканирования при первой компоновке
    LaunchedEffect(Unit) {
        activity?.let {
            val integrator = IntentIntegrator(it)
            integrator.setOrientationLocked(false)
            integrator.setPrompt("Сканируйте QR-код")
            integrator.setBeepEnabled(true)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            launcher.launch(integrator.createScanIntent())
        } ?: onCancel()
    }

    // Пока идет сканирование, можно отобразить сообщение
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Сканирование QR-кода...")
    }
}
*/
