package com.example.lenguajeseas.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lenguajeseas.R

/**
 * Clase que representa un objeto de tipo Mes.
 * @param stringResourceId El ID del recurso de cadena que representa el mes.
 * @param imageResourceId El ID del recurso de imagen que representa el mes.
 */
class Mes(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
) {
    /**
     * Propiedad de solo lectura que devuelve el nombre del mes basado en su ID de recurso de cadena.
     */
    val name: String
        get() = when (stringResourceId) {
            R.string.mes1 -> "Enero"
            R.string.mes2 -> "Febrero"
            R.string.mes3 -> "Marzo"
            R.string.mes4 -> "Abril"
            R.string.mes5 -> "Mayo"
            R.string.mes6 -> "Junio"
            R.string.mes7 -> "Julio"
            R.string.mes8 -> "Agosto"
            R.string.mes9 -> "Septiembre"
            R.string.mes10 -> "Octubre"
            R.string.mes11 -> "Noviembre"
            R.string.mes12 -> "Diciembre"
            else -> "Desconocido"
        }
}
