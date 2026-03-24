package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DividirCuentaActivity : AppCompatActivity() {
    private var personaActual = 1
    private var subtotalAcumulado = 0.0
    private val productosRestantes = ArrayList<Product>(CartManager.selectedProducts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dividir_cuenta)

        val listView = findViewById<ListView>(R.id.listViewDividir)
        val tvPersona = findViewById<TextView>(R.id.tvPersonaActual)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotalPersona)
        val btnSiguiente = findViewById<Button>(R.id.btnSiguientePersona)


        val adaptador = AdaptadorDividir(this, productosRestantes)
        listView.adapter = adaptador

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView.setOnItemClickListener { _, view, position, _ ->
            // Cambiamos el color de fondo visualmente para saber qué está seleccionado
            if (listView.isItemChecked(position)) {
                view.setBackgroundColor(getColor(R.color.naranja_primario))
            } else {
                view.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
        }

        btnSiguiente.setOnClickListener {
            val seleccionados = listView.checkedItemPositions
            val aEliminar = mutableListOf<Product>()
            var totalEstaPersona = 0.0

            // Recorremos la lista para ver qué posiciones están marcadas (true)
            for (i in 0 until productosRestantes.size) {
                if (seleccionados.get(i)) {
                    totalEstaPersona += productosRestantes[i].price
                    aEliminar.add(productosRestantes[i])
                }
            }

            if (aEliminar.isEmpty()) {
                Toast.makeText(this, "Selecciona los platos de la Persona $personaActual", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            productosRestantes.removeAll(aEliminar)

            // Notificamos al adaptador que la lista cambió
            adaptador.notifyDataSetChanged()

            // Limpiamos las selecciones del ListView para la siguiente persona
            listView.clearChoices()

            // Resetear colores de fondo de las vistas
            for (i in 0 until listView.childCount) {
                listView.getChildAt(i).setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }

            Toast.makeText(this, "Persona $personaActual pagó: $${String.format("%.2f", totalEstaPersona)}", Toast.LENGTH_LONG).show()

            if (productosRestantes.isEmpty()) {
                Toast.makeText(this, "¡Toda la cuenta ha sido dividida!", Toast.LENGTH_SHORT).show()

                CartManager.selectedProducts.clear()
                finish()
            } else {
                personaActual++
                tvPersona.text = "Asignando a: Persona $personaActual"
            }
        }
    }
    public class AdaptadorDividir(contexto: Context, producto: java.util.ArrayList<Product>) : BaseAdapter() {

        var producto: java.util.ArrayList<Product> = producto
        var contexto: Context = contexto

        override fun getCount(): Int = producto.size

        override fun getItem(position: Int): Any = producto[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val prod = producto[position]
            val vista = LayoutInflater.from(contexto).inflate(R.layout.producto_view, null)

            val imagen = vista.findViewById<ImageView>(R.id.producto_img)
            val nombre = vista.findViewById<TextView>(R.id.producto_nombre)
            val desc = vista.findViewById<TextView>(R.id.producto_desc)
            val precio = vista.findViewById<TextView>(R.id.producto_precio)

            imagen.setImageResource(prod.image)
            nombre.text = prod.name
            desc.text = prod.descripcion
            precio.text = "$${prod.price} | Cant: ${prod.cantidad}"
            return vista
        }
    }
}