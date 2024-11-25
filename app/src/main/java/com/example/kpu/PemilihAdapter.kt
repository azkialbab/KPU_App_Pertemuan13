package com.example.kpu

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kpu.database.Pemilih
import com.example.kpu.databinding.ItemPemilihBinding

class PemilihAdapter(
    private val pemilihList: List<Pemilih>,
    private val onDelete: (Pemilih) -> Unit,
    private val onEdit: (Pemilih) -> Unit,
    private val onDetail: (Pemilih) -> Unit
) : RecyclerView.Adapter<PemilihAdapter.PemilihViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemilihViewHolder {
        val binding = ItemPemilihBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PemilihViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PemilihViewHolder, position: Int) {
        val pemilih = pemilihList[position]
        holder.bind(pemilih)
    }

    override fun getItemCount(): Int = pemilihList.size

    inner class PemilihViewHolder(private val binding: ItemPemilihBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pemilih: Pemilih) {
            binding.number.text = "${adapterPosition + 1}. "
            binding.frameText.text = pemilih.name

            binding.hDelete.setOnClickListener {
                onDelete(pemilih)
            }

            binding.hEdit.setOnClickListener {
                onEdit(pemilih)
            }

            binding.hEye.setOnClickListener {
                onDetail(pemilih)
            }
        }
    }
}
