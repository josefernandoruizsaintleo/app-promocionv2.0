package com.fruiz.app_promocionv2.data.model
import com.google.firebase.Timestamp

data class Factura(
    val id: String = "",
    val clienteId: String = "",
    val promoId: String = "",
    val local: String = "",         // NUEVO
    val numero: String = "",        // NUEVO
    val monto: Double = 0.0,
    val fecha: Timestamp? = null
)
