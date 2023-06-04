package com.example.segundatentativa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.segundatentativa.databinding.ActivityFazerDoacaoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FazerDoacao : AppCompatActivity() {
    private lateinit var binding: ActivityFazerDoacaoBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFazerDoacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botaoDoar = binding.btDoar

        binding.verDoacao?.setOnClickListener {
            val ir = Intent( this, mostrarValor::class.java)
            startActivity(ir)
        }

        botaoDoar.setOnClickListener {
            val valorDoacao = binding.valorDoacao.text.toString()

            db.collection("Valor").document("Doação")
                .get()
                .addOnSuccessListener { document ->
                    val valorAtual = document.get("valor")

                    val novoValor: MutableList<String> = if (document.exists() && valorAtual is List<*>) {
                        (valorAtual as List<*>).mapNotNull { it.toString() }.toMutableList()
                    } else {
                        mutableListOf()
                    }

                    novoValor.add(valorDoacao)
                    binding.valorDoacao.text.clear() // Limpa o EditText

                    val novoValorMap = hashMapOf(
                        "valor" to novoValor
                    )

                    db.collection("Valor").document("Doação")
                        .set(novoValorMap)
                        .addOnCompleteListener {
                            Log.d("db", "Sucesso ao Salvar")
                            Toast.makeText(this, "Doação realizada com sucesso", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { exception ->
                            Log.e("db", "Erro ao salvar", exception)
                            Toast.makeText(this, "Erro ao realizar a doação", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { exception ->
                    Log.e("db", "Erro ao obter o valor atual", exception)
                    Toast.makeText(this, "Erro ao obter o valor atual da doação", Toast.LENGTH_SHORT).show()
                }
        }

        }
    }






