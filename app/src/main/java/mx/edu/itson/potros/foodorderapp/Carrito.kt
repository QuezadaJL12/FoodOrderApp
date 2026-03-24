package mx.edu.itson.potros.foodorderapp

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
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


        val adaptador = ProductosActivity.AdaptadorProductos(this, CartManager.selectedProducts)
        listView.adapter = adaptador

        tvTotal.text = "Total: $${String.format("%.22f", CartManager.getTotal())}"
    }
}