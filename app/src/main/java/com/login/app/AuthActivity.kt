package com.login.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        //Setup
        setup()
    }

    private fun setup() {

        title = "Autenticación"

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val logInButton: Button = findViewById(R.id.logInButton)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
//Logica de Boton de Registrar
        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {
                            //showHome(it.result.user.email, ProviderType.BASIC)
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }
// Logica de Boton de Acceder
        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener {

                    if (it.isSuccessful) {
                        //showHome(it.result.user.email, ProviderType.BASIC)
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}