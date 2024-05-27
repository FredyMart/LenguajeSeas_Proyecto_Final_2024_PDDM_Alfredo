package com.example.lenguajeseas.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lenguajeseas.data.Datasource


class SearchViewModel : ViewModel() {
    private val datasource = Datasource()

    fun search(query: String): List<Any> {
        val comidas = datasource.cargaComida()
        val semanas = datasource.cargaSemana()
        val meses = datasource.cargaMeses()

        val result = mutableListOf<Any>()
        result.addAll(comidas.filter { it.name.contains(query, ignoreCase = true) })
        result.addAll(semanas.filter { it.name.contains(query, ignoreCase = true) })
        result.addAll(meses.filter { it.name.contains(query, ignoreCase = true) })

        return result
    }
}

