package com.example.westocked

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EquipmentViewModel() : ViewModel() {

    private val supabaseService = SupabaseService()

    private val _equipmentList = MutableStateFlow<List<Equipment>>(emptyList())
    val equipmentList: StateFlow<List<Equipment>> get() = _equipmentList

    init {
        fetchEquipment()
    }

    private fun fetchEquipment() {
        viewModelScope.launch {
            try {
                val list = supabaseService.fetchEquipment()
                _equipmentList.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        supabaseService.close()
        super.onCleared()
    }
}
