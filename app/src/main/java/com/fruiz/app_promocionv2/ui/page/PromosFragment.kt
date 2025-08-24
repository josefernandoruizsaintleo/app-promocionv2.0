package com.fruiz.app_promocionv2.ui.page

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fruiz.app_promocionv2.R
import com.fruiz.app_promocionv2.data.FirestoreRepository
import com.fruiz.app_promocionv2.data.model.Promocion
import com.fruiz.app_promocionv2.databinding.FragmentPromosBinding
import kotlinx.coroutines.launch

class PromosFragment : Fragment() {
    private var _b: FragmentPromosBinding? = null
    private val b get() = _b!!
    private val repo = FirestoreRepository()
    private val adapter = PromoAdapter { promo ->
        // Navega a Detalle pasando promoId y titulo
        val args = Bundle().apply {
            putString("promoId", promo.promoId)
            putString("titulo", promo.titulo)
        }
        findNavController().navigate(R.id.detallePromoFragment, args)
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentPromosBinding.inflate(i, c, false)
        return b.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        b.btnBack.setOnClickListener { findNavController().navigateUp() }

        b.rvPromos.layoutManager = LinearLayoutManager(requireContext())
        b.rvPromos.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )
        b.rvPromos.adapter = adapter

        lifecycleScope.launch {
            try {
                val promos = repo.obtenerPromosActivas()
                adapter.submit(promos)
                if (promos.isEmpty()) {
                    Toast.makeText(requireContext(), "No hay promociones (puedes cargar de prueba)", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error cargando promos: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }

    // ------- Adapter súper simple con ViewHolder -------
    private class PromoAdapter(
        val onClick: (Promocion) -> Unit
    ) : RecyclerView.Adapter<PromoAdapter.VH>() {

        private val items = mutableListOf<Promocion>()

        fun submit(data: List<Promocion>) {
            items.clear(); items.addAll(data); notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_promo, parent, false)
            return VH(v)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val p = items[position]
            holder.title.text = p.titulo
            holder.desc.text = p.descripcion
            holder.itemView.setOnClickListener { onClick(p) }
        }

        override fun getItemCount() = items.size

        class VH(v: View) : RecyclerView.ViewHolder(v) {
            val title: TextView = v.findViewById(R.id.tvTitulo)
            val desc: TextView  = v.findViewById(R.id.tvDesc)
        }
    }
}