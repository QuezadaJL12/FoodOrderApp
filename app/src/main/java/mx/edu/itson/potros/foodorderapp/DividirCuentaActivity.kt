package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class DividirCuentaActivity : AppCompatActivity() {

    private var personaActual = 1
    private val productosRestantes =
        ArrayList<Product>(CartManager.selectedProducts)

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

        listView.setOnItemClickListener { _, _, _, _ ->
            adaptador.notifyDataSetChanged()

            val seleccionados = listView.checkedItemPositions
            var subtotal = 0.0

            for (i in 0 until productosRestantes.size) {
                if (seleccionados.get(i)) {
                    subtotal += productosRestantes[i].price
                }
            }

            tvSubtotal.text = "Subtotal Persona: $${String.format("%.2f", subtotal)}"
        }

        btnSiguiente.setOnClickListener {

            val seleccionados = listView.checkedItemPositions
            val aEliminar = mutableListOf<Product>()
            var totalPersona = 0.0

            for (i in 0 until productosRestantes.size) {
                if (seleccionados.get(i)) {
                    totalPersona += productosRestantes[i].price
                    aEliminar.add(productosRestantes[i])
                }
            }

            if (aEliminar.isEmpty()) {
                Toast.makeText(
                    this,
                    "Selecciona productos para Persona $personaActual",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            productosRestantes.removeAll(aEliminar)
            adaptador.notifyDataSetChanged()
            listView.clearChoices()

            Toast.makeText(
                this,
                "Persona $personaActual pagó: $${String.format("%.2f", totalPersona)}",
                Toast.LENGTH_LONG
            ).show()

            if (productosRestantes.isEmpty()) {

                Toast.makeText(this, "¡Cuenta dividida!", Toast.LENGTH_SHORT).show()

                // 🔥 AQUÍ SÍ LIMPIAMOS TODO EL CARRITO
                CartManager.selectedProducts.clear()

                finish()

            } else {
                personaActual++
                tvPersona.text = "Asignando a: Persona $personaActual"
                tvSubtotal.text = "Subtotal Persona: $0.00"
            }
        }
    }

    class AdaptadorDividir(
        val contexto: Context,
        val productos: ArrayList<Product>
    ) : BaseAdapter() {

        override fun getCount() = productos.size
        override fun getItem(position: Int) = productos[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val vista = LayoutInflater.from(contexto)
                .inflate(R.layout.item_dividir, parent, false)

            val prod = productos[position]

            val imagen = vista.findViewById<ImageView>(R.id.img_dividir)
            val nombre = vista.findViewById<TextView>(R.id.nombre_dividir)
            val precio = vista.findViewById<TextView>(R.id.precio_dividir)
            val check = vista.findViewById<CheckBox>(R.id.check_dividir)

            imagen.setImageResource(prod.image)
            nombre.text = prod.name
            precio.text = "$${prod.price}"

            val listView = parent as ListView
            check.isChecked = listView.isItemChecked(position)

            return vista
        }
    }
}