package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.databinding.FragmentLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnContinuar.setOnClickListener {
            // 1) Auth anónima
            Firebase.auth.signInAnonymously()
                .addOnSuccessListener {
                    // 2) Escritura de prueba en Firestore
                    val data = mapOf(
                        "screen" to "login",
                        "ts" to FieldValue.serverTimestamp(),
                        "ok" to true
                    )
                    Firebase.firestore.collection("debug").add(data)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Firestore OK ✅", Toast.LENGTH_SHORT).show()
                            // Si quieres, aquí ya navegas a Promos:
                            // findNavController().navigate(R.id.promosFragment)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Firestore ERROR: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Auth ERROR: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }

        binding.btnIrRegistro.setOnClickListener {
            Toast.makeText(requireContext(), "Ir a Registro", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.registroClienteFragment) // ← destino directo
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}