package mx.edu.itson.potros.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cargarCatalogoFirebase()

        val btnIniciar = findViewById<Button>(R.id.btnIniciarSesion)
        val btnRegistro = findViewById<Button>(R.id.btnRegistrar)

        btnIniciar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarCatalogoFirebase() {
        val database = FirebaseDatabase.getInstance().reference

        val comidas = arrayListOf(
            Product("Quesadillas", R.drawable.quesadilla, "Tortilla con queso adentro", 20.99, 8),
            Product("Huaraches", R.drawable.huarache, "Tortilla gruesa con frijoles", 60.99, 10),
            Product("Hamburguesa", R.drawable.hamburguesa, "Res, pollo o vegana", 150.99, 12),
            Product("Sushi", R.drawable.sushi, "Platillo japonés tradicional", 119.99, 11),
            Product("Ceviche", R.drawable.ceviche, "Pescado o camarón", 57.99, 14),
            Product("Flan", R.drawable.flan, "Postre tradicional de caramelo", 25.99, 19),
            Product("Pastel de chocolate", R.drawable.pastel, "Pastel de chocolate pequeño", 56.99, 15)
        )

        val bebidas = arrayListOf(
            Product("Coca-Cola", R.drawable.coca, "20 oz.", 14.99, 15),
            Product("Pepsi", R.drawable.pepsi, "20 oz.", 13.95, 14),
            Product("Agua fresca", R.drawable.aguafresca, "Bebida natural de frutas", 20.99, 50)
        )

        comidas.forEach { producto ->
            database.child("productos").child("comidas").push().setValue(producto)
        }

        bebidas.forEach { producto ->
            database.child("productos").child("bebidas").push().setValue(producto)
        }
    }
}