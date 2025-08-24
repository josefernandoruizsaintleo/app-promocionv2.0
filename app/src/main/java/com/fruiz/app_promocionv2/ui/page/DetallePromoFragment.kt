package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.data.FirestoreRepository
import com.fruiz.app_promocionv2.databinding.FragmentDetallePromoBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch

class DetallePromoFragment : Fragment() {
    private var _b: FragmentDetallePromoBinding? = null
    private val b get() = _b!!
    private val repo = FirestoreRepository()
    private var promoId: String = ""
    private var titulo: String = ""

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentDetallePromoBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        promoId = arguments?.getString("promoId").orEmpty()
        titulo  = arguments?.getString("titulo").orEmpty()

        b.tvTitulo.text = titulo
        b.btnBack.setOnClickListener { findNavController().navigateUp() }

        // botones
        b.btnRegistrarFactura.setOnClickListener {
            val args = Bundle().apply { putString("promoId", promoId); putString("titulo", titulo) }
            findNavController().navigate(R.id.registroFacturaFragment, args)
        }
        b.btnVerFacturas.setOnClickListener {
            val args = Bundle().apply { putString("promoId", promoId); putString("titulo", titulo) }
            findNavController().navigate(R.id.listadoFacturasFragment, args)
        }

        // cargar oportunidades
        val uid = Firebase.auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(requireContext(), "Sin sesión", Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch {
            try {
                val opp = repo.calcularOportunidades(uid, promoId)
                b.tvOportunidades.text = opp.toInt().toString() // 1 USD = 1 oportunidad
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}