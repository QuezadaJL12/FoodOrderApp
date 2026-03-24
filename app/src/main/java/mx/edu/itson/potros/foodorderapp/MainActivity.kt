package mx.edu.itson.potros.foodorderapp

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

        // Acción al presionar Iniciar Sesión
        btnIniciar.setOnClickListener {
            Toast.makeText(this, "Redirigiendo al Menú...", Toast.LENGTH_SHORT).show()

        }

        // Acción al presionar Registrar
        btnRegistro.setOnClickListener {
            Toast.makeText(this, "Abriendo pantalla de Registro...", Toast.LENGTH_SHORT).show()

        }
    }
}