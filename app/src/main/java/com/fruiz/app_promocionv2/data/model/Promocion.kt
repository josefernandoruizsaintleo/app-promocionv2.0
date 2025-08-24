package com.fruiz.app_promocionv2.data.model

import com.google.firebase.Timestamp

data class Promocion(
    val promoId: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val fechaInicio: Timestamp? = null,
    val fechaFin: Timestamp? = null,
    val activa: Boolean = true
)