package mx.edu.itson.potros.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIniciar = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRegistro = findViewById<Button>(R.id.btnRegistrar)


        btnIniciar.setOnClickListener {
            Toast.makeText(this, "Abriendo Inicio de Sesión...", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        btnRegistro.setOnClickListener {
            Toast.makeText(this, "Abriendo pantalla de Registro...", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}