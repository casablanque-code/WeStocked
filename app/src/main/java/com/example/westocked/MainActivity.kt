package com.example.westocked

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.westocked.ui.theme.WeStockedTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeStockedTheme {
                EquipmentListScreen()
                /*WeStockedTheme {
                Surface {*/
            }
        }
    }}
