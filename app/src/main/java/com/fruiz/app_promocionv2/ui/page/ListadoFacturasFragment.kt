package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.data.FirestoreRepository
import com.fruiz.app_promocionv2.data.model.Factura
import com.fruiz.app_promocionv2.databinding.FragmentListadoFacturasBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ListadoFacturasFragment : Fragment() {
    private var _b: FragmentListadoFacturasBinding? = null
    private val b get() = _b!!
    private val repo = FirestoreRepository()
    private var promoId: String = ""

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentListadoFacturasBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        promoId = arguments?.getString("promoId").orEmpty()
        b.btnBack.setOnClickListener { findNavController().navigateUp() }

        val adapter = FacturasAdapter()
        b.rvFacturas.layoutManager = LinearLayoutManager(requireContext())
        b.rvFacturas.adapter = adapter

        val uid = Firebase.auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(requireContext(), "Sin sesión", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val uid = Firebase.auth.currentUser?.uid ?: return@launch
                val facturas = repo.obtenerFacturas(uid, promoId)
                adapter.submit(facturas)
            } catch (e: Exception) {
                Toast.makeText(requireContext(),
                    "No se pudieron cargar las facturas: ${e.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }

    private class FacturasAdapter : RecyclerView.Adapter<FacturasAdapter.VH>() {
        private val items = mutableListOf<Factura>()

        fun submit(list: List<Factura>) {
            items.clear(); items.addAll(list); notifyDataSetChanged()
        }

        override fun onCreateViewHolder(p: ViewGroup, vt: Int): VH {
            val v = LayoutInflater.from(p.context).inflate(R.layout.item_factura, p, false)
            return VH(v)
        }

        override fun onBindViewHolder(h: VH, i: Int) {
            val f = items[i]
            h.bind(f)
        }

        override fun getItemCount() = items.size

        class VH(v: View) : RecyclerView.ViewHolder(v) {
            private val l1: TextView = v.findViewById(R.id.tvLinea1)
            private val l2: TextView = v.findViewById(R.id.tvLinea2)
            private val l3: TextView = v.findViewById(R.id.tvLinea3)

            fun bind(f: Factura) {
                // Fecha formateada (si existe)
                val fechaTxt = f.fecha?.toDate()?.let {
                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault()).format(it)
                } ?: "—"

                l1.text = "Local: ${f.local}    Nº: ${f.numero}"
                l2.text = "Monto: ${"%.2f".format(f.monto)}    Promo: ${f.promoId}"
                l3.text = "Fecha: $fechaTxt"
            }
        }
    }

}