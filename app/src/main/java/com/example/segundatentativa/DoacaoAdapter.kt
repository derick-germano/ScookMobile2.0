package com.example.segundatentativa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.segundatentativa.databinding.ItemDoacaoBinding

class DoacaoAdapter(private val listaDoacoes: List<String>) :
    RecyclerView.Adapter<DoacaoAdapter.DoacaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoacaoViewHolder {
        val binding = ItemDoacaoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoacaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoacaoViewHolder, position: Int) {
        val doacao = listaDoacoes[position]
        holder.bind(doacao)
    }

    override fun getItemCount(): Int {
        return listaDoacoes.size
    }

    class DoacaoViewHolder(private val binding: ItemDoacaoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(doacao: String) {
            binding.textViewValor.text = doacao
        }
    }
}