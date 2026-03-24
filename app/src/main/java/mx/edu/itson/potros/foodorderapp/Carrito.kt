package mx.edu.itson.potros.foodorderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Carrito : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val listView: ListView = findViewById(R.id.listViewCarrito)
        val tvTotal: TextView = findViewById(R.id.tvTotal)
        val btnPagar: Button = findViewById(R.id.btnPagar)
        val adaptador = ProductosActivity.AdaptadorProductos(this, CartManager.selectedProducts)
        listView.adapter = adaptador

        tvTotal.text = "Total: $${String.format("%.2f", CartManager.getTotal())}"
        btnPagar.setOnClickListener {
            // Verificamos que el carrito no esté vacío antes de pasar a dividir
            if (CartManager.selectedProducts.isNotEmpty()) {
                val intent = Intent(this, DividirCuentaActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay productos para pagar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}