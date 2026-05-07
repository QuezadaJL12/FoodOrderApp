package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
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

        listView.setOnItemClickListener { _, _, position, _ ->
            // Forzamos al adaptador a redibujarse para que el CheckBox se marque
            adaptador.notifyDataSetChanged()

            // Calcular subtotal de lo seleccionado actualmente
            val seleccionados = listView.checkedItemPositions
            var subtotalEstaPersona = 0.0
            for (i in 0 until productosRestantes.size) {
                if (seleccionados.get(i)) {
                    subtotalEstaPersona += productosRestantes[i].price
                }
            }
            tvSubtotal.text = "Subtotal Persona: $${String.format("%.2f", subtotalEstaPersona)}"
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
                tvSubtotal.text = "Subtotal Persona: $0.00" // Resetear texto
            }
        }
    }
    class AdaptadorDividir(val contexto: Context, val productos: ArrayList<Product>) : BaseAdapter() {

        override fun getCount(): Int = productos.size
        override fun getItem(position: Int): Any = productos[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val vista = LayoutInflater.from(contexto).inflate(R.layout.item_dividir, parent, false)
            val prod = productos[position]

            val imagen = vista.findViewById<ImageView>(R.id.img_dividir)
            val nombre = vista.findViewById<TextView>(R.id.nombre_dividir)
            val precio = vista.findViewById<TextView>(R.id.precio_dividir)
            val check = vista.findViewById<CheckBox>(R.id.check_dividir)

            imagen.setImageResource(prod.image)
            nombre.text = prod.name
            precio.text = "$${prod.price}"

            // Sincronizar el checkbox con el estado del ListView
            val listView = parent as ListView
            check.isChecked = listView.isItemChecked(position)

            return vista
        }
    }
}