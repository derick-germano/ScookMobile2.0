package com.example.segundatentativa

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.segundatentativa.databinding.ActivityCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCadastrar.setOnClickListener {

            val email = binding.cdEmail.text.toString()
            val senha = binding.cdSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                val snackbar = Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { cadastro ->
                        if (cadastro.isSuccessful) {
                            val snackbar = Snackbar.make(
                                it,
                                "Sucesso ao cadastrar Usuário!",
                                Snackbar.LENGTH_LONG
                            )
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()
                            binding.cdEmail.setText("")
                            binding.cdSenha.setText("")
                            atrasarTransicao()
                        }
                    }.addOnFailureListener { exception ->

                    val mensagemErro = when (exception) {
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                        is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada!"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Erro ao cadastrar usuário!"
                    }
                    val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.BLUE)
                    snackbar.show()
                }
            }
        }

    }

    private fun atrasarTransicao() {
        val handler = Handler()
        handler.postDelayed({
            irParaLogin()
        }, 2000) // Atraso de 2 segundos (2000 milissegundos)
    }

    private fun irParaLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}