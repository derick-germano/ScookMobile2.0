package com.example.segundatentativa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.segundatentativa.databinding.ActivityMainBinding
import com.example.segundatentativa.databinding.ItemDoacaoBinding
import com.google.firebase.firestore.FirebaseFirestore

class mostrarValor : AppCompatActivity() {
    private lateinit var binding: ItemDoacaoBinding
    private lateinit var doacaoAdapter: DoacaoAdapter
    private val doacoesList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemDoacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doacaoAdapter = DoacaoAdapter(doacoesList)
        binding.recyclerViewDoacoes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDoacoes.adapter = doacaoAdapter

        // Recupere os valores da doação do Firebase
        val db = FirebaseFirestore.getInstance()
        db.collection("Valor").document("Doação")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val valorAtual = document.get("valor")
                    if (valorAtual is List<*>) {
                        for (valor in valorAtual) {
                            if (valor is String) {
                                doacoesList.add(valor)
                            }
                        }
                    }
                    doacaoAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                // Trate a falha ao obter os valores da doação do Firebase
            }
    }
}