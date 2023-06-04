package com.example.segundatentativa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.segundatentativa.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuarioNome = binding.tfUsuario
        val nome = intent.getStringExtra("nome")
        usuarioNome.text = nome

        binding.btDoacao.setOnClickListener {
            val doar = Intent(this, FazerDoacao::class.java)
            startActivity(doar)
        }

        binding.btSair.setOnClickListener {
            val sair = Intent(this, MainActivity::class.java )
            startActivity(sair)
        }

    }


}