package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fruiz.app_promocionv2.data.FirestoreRepository
import com.fruiz.app_promocionv2.databinding.FragmentRegistroFacturaBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class RegistroFacturaFragment : Fragment() {
    private var _b: FragmentRegistroFacturaBinding? = null
    private val b get() = _b!!
    private val repo = FirestoreRepository()
    private var promoId: String = ""

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentRegistroFacturaBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        promoId = arguments?.getString("promoId").orEmpty()
        b.btnBack.setOnClickListener { findNavController().navigateUp() }

        // dentro de onViewCreated, reemplaza el setOnClickListener de btnGuardarFactura:
        b.btnGuardarFactura.setOnClickListener {
            val local  = b.etLocal.text?.toString()?.trim().orEmpty()
            val numero = b.etNumero.text?.toString()?.trim().orEmpty()
            val monto  = b.etMonto.text?.toString()?.toDoubleOrNull() ?: 0.0
            val uid    = Firebase.auth.currentUser?.uid

            if (uid == null) {
                Toast.makeText(requireContext(), "Sin sesión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (local.isEmpty() || numero.isEmpty() || monto <= 0.0) {
                Toast.makeText(requireContext(), "Completa Local, Número y Monto > 0", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    repo.registrarFactura(uid, promoId, local, numero, monto) // <-- ahora pasa local y numero
                    Toast.makeText(requireContext(), "Factura guardada", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}