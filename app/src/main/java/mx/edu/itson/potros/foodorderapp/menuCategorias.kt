package mx.edu.itson.potros.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
        val btnVerCarrito: Button = findViewById(R.id.btn_ver_carrito)


        fun goToProductos(menuType: String) {
            val intent = Intent(this, ProductosActivity::class.java)
            intent.putExtra("menuType", menuType)
            startActivity(intent)
        }

        // Clicks
        btnPlatillos.setOnClickListener { goToProductos("Platillos") }
        btnPostres.setOnClickListener { goToProductos("Postres") }
        btnBebidas.setOnClickListener { goToProductos("Bebidas") }
        btnVerCarrito.setOnClickListener {
            // Validamos si hay algo en el carrito antes de ir
            if (CartManager.selectedProducts.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, Carrito::class.java)
                startActivity(intent)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}