package com.example.lenguajeseas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenguajeseas.model.Semana
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona la lista de semanas favoritas.
 */
class SemanasViewModel : ViewModel() {

    // Flujo mutable que contiene la lista de semanas favoritas
    private val _favoriteSemanas = MutableStateFlow<List<Semana>>(emptyList())
    // Flujo de solo lectura para exponer la lista de semanas favoritas al exterior
    val favoriteSemanas: StateFlow<List<Semana>> get() = _favoriteSemanas

    /**
     * Añade una semana a la lista de favoritos.
     * @param semana La semana a añadir.
     */
    fun addFavorite(semana: Semana) {
        // Inicia un nuevo trabajo en el viewModelScope (corutina de ViewModel)
        viewModelScope.launch {
            // Crea una lista mutable a partir de la lista actual de favoritos
            val currentFavorites = _favoriteSemanas.value.toMutableList()
            // Verifica si la semana no está en la lista actual de favoritos
            if (!currentFavorites.contains(semana)) {
                // Añade la semana a la lista de favoritos
                currentFavorites.add(semana)
                // Actualiza el valor del flujo con la nueva lista de favoritos
                _favoriteSemanas.value = currentFavorites
            }
        }
    }

    /**
     * Elimina una semana de la lista de favoritos.
     * @param semana La semana a eliminar.
     */
    fun removeFavorite(semana: Semana) {
        // Inicia un nuevo trabajo en el viewModelScope (corutina de ViewModel)
        viewModelScope.launch {
            // Crea una lista mutable a partir de la lista actual de favoritos
            val currentFavorites = _favoriteSemanas.value.toMutableList()
            // Verifica si la semana está en la lista actual de favoritos
            if (currentFavorites.contains(semana)) {
                // Elimina la semana de la lista de favoritos
                currentFavorites.remove(semana)
                // Actualiza el valor del flujo con la nueva lista de favoritos
                _favoriteSemanas.value = currentFavorites
            }
        }
    }

    /**
     * Verifica si una semana está en la lista de favoritos.
     * @param semana La semana a verificar.
     * @return true si la semana está en la lista de favoritos, false en caso contrario.
     */
    fun isFavorite(semana: Semana): Boolean {
        // Verifica si la semana está contenida en la lista de favoritos
        return _favoriteSemanas.value.contains(semana)
    }
}

