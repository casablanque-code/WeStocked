/*
package com.example.westocked

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(
    private val onQrCodeScanned: (String) -> Unit
) : ImageAnalysis.Analyzer {

    // Получаем экземпляр клиента для сканирования штрих-кодов (QR-кодов)
    private val scanner = BarcodeScanning.getClient()

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            // Преобразуем кадр в InputImage, учитывая поворот камеры
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            // Запускаем процесс сканирования
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    // Если найден хотя бы один штрих-код, извлекаем его значение
                    barcodes.firstOrNull()?.rawValue?.let { code ->
                        onQrCodeScanned(code)
                    }
                }
                .addOnFailureListener {
                    // Здесь можно обработать ошибку, если необходимо
                }
                .addOnCompleteListener {
                    // Обязательно закрываем imageProxy, чтобы не заблокировать поток
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }
}
*/
