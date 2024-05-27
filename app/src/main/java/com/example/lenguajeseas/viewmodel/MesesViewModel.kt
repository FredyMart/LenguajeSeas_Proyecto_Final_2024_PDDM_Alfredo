package com.example.lenguajeseas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenguajeseas.model.Mes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona la lista de meses favoritos.
 */
class MesesViewModel : ViewModel() {

    // Flujo mutable que contiene la lista de meses favoritos
    private val _favoriteMeses = MutableStateFlow<List<Mes>>(emptyList())
    // Flujo de solo lectura para exponer la lista de meses favoritos al exterior
    val favoriteMeses: StateFlow<List<Mes>> get() = _favoriteMeses

    /**
     * Añade un mes a la lista de favoritos.
     * @param mes El mes a añadir.
     */
    fun addFavorite(mes: Mes) {
        // Inicia un nuevo trabajo en el viewModelScope (corutina de ViewModel)
        viewModelScope.launch {
            // Crea una lista mutable a partir de la lista actual de favoritos
            val currentFavorites = _favoriteMeses.value.toMutableList()
            // Verifica si el mes no está en la lista actual de favoritos
            if (!currentFavorites.contains(mes)) {
                // Añade el mes a la lista de favoritos
                currentFavorites.add(mes)
                // Actualiza el valor del flujo con la nueva lista de favoritos
                _favoriteMeses.value = currentFavorites
            }
        }
    }

    /**
     * Elimina un mes de la lista de favoritos.
     * @param mes El mes a eliminar.
     */
    fun removeFavorite(mes: Mes) {
        // Inicia un nuevo trabajo en el viewModelScope (corutina de ViewModel)
        viewModelScope.launch {
            // Crea una lista mutable a partir de la lista actual de favoritos
            val currentFavorites = _favoriteMeses.value.toMutableList()
            // Verifica si el mes está en la lista actual de favoritos
            if (currentFavorites.contains(mes)) {
                // Elimina el mes de la lista de favoritos
                currentFavorites.remove(mes)
                // Actualiza el valor del flujo con la nueva lista de favoritos
                _favoriteMeses.value = currentFavorites
            }
        }
    }

    /**
     * Verifica si un mes está en la lista de favoritos.
     * @param mes El mes a verificar.
     * @return true si el mes está en la lista de favoritos, false en caso contrario.
     */
    fun isFavorite(mes: Mes): Boolean {
        // Verifica si el mes está contenido en la lista de favoritos
        return _favoriteMeses.value.contains(mes)
    }
}
