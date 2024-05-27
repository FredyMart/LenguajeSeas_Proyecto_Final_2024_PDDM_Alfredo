package com.example.lenguajeseas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona la lista de elementos favoritos.
 */
class FavoritesViewModel : ViewModel() {

    // Flujo mutable que contiene la lista de identificadores de elementos favoritos
    private val _favorites = MutableStateFlow<List<Int>>(emptyList())
    // Flujo de solo lectura para exponer la lista de favoritos al exterior
    val favorites: StateFlow<List<Int>> get() = _favorites

    /**
     * Añade un elemento a la lista de favoritos.
     * @param resourceId El identificador del recurso a añadir.
     */
    fun addFavorite(resourceId: Int) {
        // Inicia un nuevo trabajo en el viewModelScope (corutina de ViewModel)
        viewModelScope.launch {
            // Actualiza el valor del flujo, añadiendo el resourceId a la lista existente
            _favorites.value = _favorites.value + resourceId
        }
    }

    /**
     * Elimina un elemento de la lista de favoritos.
     * @param resourceId El identificador del recurso a eliminar.
     */
    fun removeFavorite(resourceId: Int) {
        // Inicia un nuevo trabajo en el viewModelScope (corutina de ViewModel)
        viewModelScope.launch {
            // Actualiza el valor del flujo, eliminando el resourceId de la lista existente
            _favorites.value = _favorites.value - resourceId
        }
    }

    /**
     * Verifica si un elemento está en la lista de favoritos.
     * @param resourceId El identificador del recurso a verificar.
     * @return true si el elemento está en la lista de favoritos, false en caso contrario.
     */
    fun isFavorite(resourceId: Int): Boolean {
        // Devuelve true si el resourceId está contenido en la lista de favoritos, false en caso contrario
        return _favorites.value.contains(resourceId)
    }
}
