package mx.edu.itson.potros.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class menuCategorias : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_categorias)

        val btnPlatillos: Button = findViewById(R.id.button_platillos)
        val btnPostres: Button = findViewById(R.id.button_postres)
        val btnBebidas: Button = findViewById(R.id.button_bebidas)

        // Función reutilizable (igual que te enseñé antes)
        fun goToProductos(menuType: String) {
            val intent = Intent(this, ProductosActivity::class.java)
            intent.putExtra("menuType", menuType)
            startActivity(intent)
        }

        // Clicks
        btnPlatillos.setOnClickListener { goToProductos("Platillos") }
        btnPostres.setOnClickListener { goToProductos("Postres") }
        btnBebidas.setOnClickListener { goToProductos("Bebidas") }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}