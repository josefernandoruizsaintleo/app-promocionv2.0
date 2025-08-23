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
            Toast.makeText(requireContext(), "Ir a Promos", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.promosFragment)   // ← destino directo
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
