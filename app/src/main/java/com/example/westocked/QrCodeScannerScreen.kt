@file:Suppress("DEPRECATION")

package com.example.westocked

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

/**
 * Этот composable запускает сканирование QR-кода с помощью ZXing.
 *
 * Если сканирование успешно – результат (число) передаётся в onResult.
 * Если сканирование отменено – вызывается onCancel.
 */
@Composable
fun QrCodeScannerScreen(
    onResult: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    // Приводим context к ComponentActivity
    val activity = context as? ComponentActivity

    // Создаем launcher для запуска QR-сканера
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val intentResult: IntentResult? =
                IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            val rawValue = intentResult?.contents
            val firstLine = rawValue?.lines()?.firstOrNull()
            val number = firstLine?.toLongOrNull()
            onResult(number)
        } else {
            onCancel()
        }
    }

    // Запускаем сканирование при входе в композицию
    LaunchedEffect(Unit) {
        if (activity != null) {
            val integrator = IntentIntegrator(activity)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            integrator.setPrompt("Отсканируйте QR-код")
            integrator.setCameraId(0) // Используем заднюю камеру
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(false)
            val intent: Intent = integrator.createScanIntent()
            launcher.launch(intent)
        } else {
            onCancel()
        }
    }
}
