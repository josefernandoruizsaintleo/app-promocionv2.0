package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.databinding.FragmentDetallePromoBinding

class DetallePromoFragment : Fragment() {
    private var _b: FragmentDetallePromoBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentDetallePromoBinding.inflate(i, c, false).also { _b = it }.root
    override fun onViewCreated(v: View, s: Bundle?) {
        b.btnRegistrarFactura.setOnClickListener { findNavController().navigate(R.id.action_detalle_to_regFactura) }
        b.btnVerFacturas.setOnClickListener     { findNavController().navigate(R.id.action_detalle_to_listado) }
        b.btnBack.setOnClickListener            { findNavController().navigateUp() }
    }
    override fun onDestroyView() { _b = null; super.onDestroyView() }
}