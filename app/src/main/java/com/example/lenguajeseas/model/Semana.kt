package com.example.lenguajeseas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lenguajeseas.R

/**
 * Clase que representa un objeto de tipo Semana.
 * @param stringResourceId El ID del recurso de cadena que representa el día de la semana.
 * @param imageResourceId El ID del recurso de imagen que representa el día de la semana.
 */
class Semana(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
) {
    /**
     * Propiedad de solo lectura que devuelve el nombre del día de la semana basado en su ID de recurso de cadena.
     */
    val name: String
        get() = when (stringResourceId) {
            R.string.semana1 -> "Lunes"
            R.string.semana2 -> "Martes"
            R.string.semana3 -> "Miércoles"
            R.string.semana4 -> "Jueves"
            R.string.semana5 -> "Viernes"
            R.string.semana6 -> "Sábado"
            R.string.semana7 -> "Domingo"
            else -> "Desconocido"
        }
}

