package mx.edu.itson.potros.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val btnEntrar = findViewById<Button>(R.id.btnLoginEntrar)
        val etLoginCorreo = findViewById<EditText>(R.id.etLoginCorreo)
        val etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)

        btnEntrar.setOnClickListener {
            val correo = etLoginCorreo.text.toString().trim()
            val pass = etLoginPassword.text.toString().trim()

            if (correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "¡Bienvenido de nuevo!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, menuCategorias::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        try {
                            throw task.exception!!
                        } catch (e: com.google.firebase.auth.FirebaseAuthInvalidUserException) {
                            // Usuario no existe o fue deshabilitado
                            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_LONG).show()

                        } catch (e: com.google.firebase.auth.FirebaseAuthInvalidCredentialsException) {
                            // Contraseña incorrecta
                            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_LONG).show()

                        } catch (e: Exception) {
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }
}