package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.data.FirestoreRepository
import com.fruiz.app_promocionv2.data.model.Usuario
import com.fruiz.app_promocionv2.databinding.FragmentRegistroClienteBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class RegistroClienteFragment : Fragment() {
    private var _b: FragmentRegistroClienteBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View =
        FragmentRegistroClienteBinding.inflate(i, c, false).also { _b = it }.root

    override fun onViewCreated(v: View, s: Bundle?) {
        b.btnGuardar.setOnClickListener { crearCuenta() }
        b.btnBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun crearCuenta() {
        val nombre   = b.etNombre.text?.toString()?.trim().orEmpty()
        val telefono = b.etTelefono.text?.toString()?.trim().orEmpty()
        val email    = b.etEmail.text?.toString()?.trim().orEmpty()
        val pass     = b.etPassword.text?.toString()?.trim().orEmpty()

        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(requireContext(), "Completa nombre, email y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear usuario en Auth
        Firebase.auth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid ?: return@addOnSuccessListener
                // Guardar datos en Firestore
                val repo = FirestoreRepository()
                val u = Usuario(uid = uid, nombre = nombre, telefono = telefono, email = email)
                lifecycleScope.launch {
                    try {
                        repo.guardarUsuario(u)
                        Toast.makeText(requireContext(), "Cuenta creada ✅", Toast.LENGTH_SHORT).show()
                        // Ir a Promos
                        findNavController().navigate(R.id.promosFragment)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error guardando datos: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error creando cuenta: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}