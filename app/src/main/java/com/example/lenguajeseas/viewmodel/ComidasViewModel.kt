// Define el paquete al que pertenece el ViewModel.
package com.example.lenguajeseas.viewmodel

// Importaciones necesarias para el ViewModel.
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lenguajeseas.model.Comida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Declaración del ViewModel para manejar comidas favoritas.
class ComidasViewModel : ViewModel() {
    // Campo privado que mantiene el estado de las comidas favoritas usando MutableStateFlow.
    private val _favoriteComidas = MutableStateFlow<List<Comida>>(emptyList())
    // Propiedad pública solo de lectura que expone el estado de las comidas favoritas.
    val favoriteComidas: StateFlow<List<Comida>> get() = _favoriteComidas

    // Método para agregar una comida a la lista de favoritos.
    fun addFavorite(comida: Comida) {
        // Lanza una nueva coroutine dentro del alcance del ViewModel.
        viewModelScope.launch {
            // Crea una copia mutable de la lista actual de favoritos.
            val currentFavorites = _favoriteComidas.value.toMutableList()
            // Añade la comida a la lista si no está presente.
            if (!currentFavorites.contains(comida)) {
                currentFavorites.add(comida)
                // Actualiza el estado con la nueva lista de favoritos.
                _favoriteComidas.value = currentFavorites
            }
        }
    }

    // Método para remover una comida de la lista de favoritos.
    fun removeFavorite(comida: Comida) {
        // Lanza una nueva coroutine dentro del alcance del ViewModel.
        viewModelScope.launch {
            // Crea una copia mutable de la lista actual de favoritos.
            val currentFavorites = _favoriteComidas.value.toMutableList()
            // Remueve la comida de la lista si está presente.
            if (currentFavorites.contains(comida)) {
                currentFavorites.remove(comida)
                // Actualiza el estado con la nueva lista de favoritos.
                _favoriteComidas.value = currentFavorites
            }
        }
    }

    // Método para verificar si una comida está en la lista de favoritos.
    fun isFavorite(comida: Comida): Boolean {
        // Devuelve true si la comida está en la lista de favoritos.
        return _favoriteComidas.value.contains(comida)
    }
}
