package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnEntrar = findViewById<Button>(R.id.btnLoginEntrar)
        val etLoginCorreo = findViewById<EditText>(R.id.etLoginCorreo)
        val etLoginPassword = findViewById<EditText>(R.id.etLoginPassword)

        btnEntrar.setOnClickListener {
            val correoIngresado = etLoginCorreo.text.toString()
            val passIngresada = etLoginPassword.text.toString()


            val sharedPreferences = getSharedPreferences("MisDatos", Context.MODE_PRIVATE)
            val correoGuardado = sharedPreferences.getString("correoGuardado", "")
            val passGuardada = sharedPreferences.getString("passwordGuardada", "")


            if (correoIngresado == correoGuardado && passIngresada == passGuardada && correoGuardado != "") {
                Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
