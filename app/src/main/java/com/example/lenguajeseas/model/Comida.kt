package com.example.lenguajeseas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lenguajeseas.R

/**
 * Clase que representa un objeto de tipo Comida.
 * @param stringResourceId El ID del recurso de cadena que representa la comida.
 * @param imageResourceId El ID del recurso de imagen que representa la comida.
 */
class Comida(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
) {
    /**
     * Propiedad de solo lectura que devuelve el nombre de la comida basado en su ID de recurso de cadena.
     */
    val name: String
        get() = when (stringResourceId) {
            R.string.comida1 -> "Para Pedir Cena"
            R.string.comida2 -> "Para Pedir Comida"
            R.string.comida3 -> "Para Pedir Desayuno"
            R.string.comida4 -> "Para Pedir Merienda"
            else -> "Desconocido"
        }
}
