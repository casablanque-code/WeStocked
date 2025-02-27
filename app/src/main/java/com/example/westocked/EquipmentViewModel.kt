package com.example.westocked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EquipmentViewModel : ViewModel() {

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
                _equipmentList.value = list.sortedBy { it.inventory_number }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateEquipment(updated: Equipment) {
        viewModelScope.launch {
            try {
                val success = supabaseService.updateEquipment(updated)
                if (success) {
                    fetchEquipment()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addEquipment(newEquipment: Equipment) {
        viewModelScope.launch {
            try {
                val success = supabaseService.addEquipment(newEquipment)
                if (success) {
                    fetchEquipment()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
