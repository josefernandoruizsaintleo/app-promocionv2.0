package com.fruiz.app_promocionv2.data

import com.fruiz.app_promocionv2.data.model.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val db = Firebase.firestore
    private val colUsuarios get() = db.collection("usuarios")
    private val colPromos get() = db.collection("promociones")
    private val colFacturas get() = db.collection("facturas")

    // ---------- Usuarios ----------
    suspend fun guardarUsuario(u: Usuario) {
        require(u.uid.isNotBlank()) { "uid requerido" }
        colUsuarios.document(u.uid).set(u).await()
    }

    suspend fun obtenerUsuario(uid: String): Usuario? {
        val snap = colUsuarios.document(uid).get().await()
        return snap.toObject(Usuario::class.java)
    }

    // ---------- Promociones ----------
    suspend fun upsertPromocion(p: Promocion) {
        require(p.promoId.isNotBlank()) { "promoId requerido" }
        colPromos.document(p.promoId).set(p).await()
    }

    suspend fun obtenerPromosActivas(): List<Promocion> {
        val q = colPromos.whereEqualTo("activa", true)
        return q.get().await().documents.mapNotNull { it.toObject(Promocion::class.java) }
    }

    // ---------- Facturas ----------
    suspend fun registrarFactura(
        clienteId: String,
        promoId: String,
        local: String,             // NUEVO
        numero: String,            // NUEVO
        monto: Double,
        fecha: Timestamp = Timestamp.now()
    ): String {
        require(clienteId.isNotBlank() && promoId.isNotBlank()) { "clienteId y promoId requeridos" }
        val data = hashMapOf(
            "clienteId" to clienteId,
            "promoId" to promoId,
            "local" to local,      // NUEVO
            "numero" to numero,    // NUEVO
            "monto" to monto,
            "fecha" to fecha
        )
        val ref = colFacturas.add(data).await()
        return ref.id
    }

    suspend fun obtenerFacturas(clienteId: String, promoId: String? = null): List<Factura> {
        var q: Query = colFacturas.whereEqualTo("clienteId", clienteId)
        if (!promoId.isNullOrBlank()) q = q.whereEqualTo("promoId", promoId)
        val snaps = q.get().await()
        return snaps.documents.map { d ->
            Factura(
                id        = d.id,
                clienteId = d.getString("clienteId") ?: "",
                promoId   = d.getString("promoId") ?: "",
                local     = d.getString("local") ?: "",     // NUEVO
                numero    = d.getString("numero") ?: "",    // NUEVO
                monto     = d.getDouble("monto") ?: 0.0,
                fecha     = d.getTimestamp("fecha")
            )
        }
    }

    // ---------- Oportunidades ----------
    /** 1 dólar = 1 oportunidad. Suma de monto por cliente-promo */
    suspend fun calcularOportunidades(clienteId: String, promoId: String): Double {
        val facturas = obtenerFacturas(clienteId, promoId)
        return facturas.sumOf { it.monto }
    }
}

