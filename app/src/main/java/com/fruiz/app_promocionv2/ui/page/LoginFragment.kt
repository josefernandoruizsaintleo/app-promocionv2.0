package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {
    private var _b: FragmentLoginBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentLoginBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        // Ir a registro (crear cuenta)
        b.btnIrRegistro.setOnClickListener {
            findNavController().navigate(R.id.registroClienteFragment)
        }

        // Entrar con email/clave
        b.btnContinuar.setOnClickListener {
            val email = b.etEmail.text?.toString()?.trim().orEmpty()
            val pass  = b.etPassword.text?.toString()?.trim().orEmpty()
            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Ingresa email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Bienvenido", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.promosFragment)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error de inicio: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}