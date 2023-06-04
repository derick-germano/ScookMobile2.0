package com.example.segundatentativa

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.segundatentativa.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoLogin.setOnClickListener { view ->

            val email = binding.nomeLogin.text.toString()
            val senha = binding.senhaLogin.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar =
                    Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { autenticacao ->
                        if (autenticacao.isSuccessful) {
                            navegarTelaHome()
                        }
                    }.addOnFailureListener {
                    val snackbar = Snackbar.make(
                        view,
                        "Erro ao fazer o login do usuário!",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            }


        }

        binding.clickCd?.setOnClickListener {
            val irCadatrar = Intent(this, Cadastro::class.java)
            startActivity(irCadatrar)
        }
    }

    private fun navegarTelaHome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}