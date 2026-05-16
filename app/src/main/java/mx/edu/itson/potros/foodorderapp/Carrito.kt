package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class Carrito : AppCompatActivity() {

    private lateinit var adaptador: AdaptadorCarrito
    private lateinit var tvTotal: TextView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvIVA: TextView

    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val listView: ListView = findViewById(R.id.listViewCarrito)

        tvTotal = findViewById(R.id.tvTotal)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvIVA = findViewById(R.id.tvIVA)

        val btnPagar: Button = findViewById(R.id.btnPagar)

        adaptador = AdaptadorCarrito(this, CartManager.selectedProducts) {
            actualizarTotal()
        }

        listView.adapter = adaptador
        actualizarTotal()

        btnPagar.setOnClickListener {

            if (CartManager.selectedProducts.isNotEmpty()) {

                val total = CartManager.getTotal()
                val subtotal = total / 1.16
                val iva = total - subtotal

                val productosFirebase = CartManager.selectedProducts.map { prod ->
                    hashMapOf(
                        "name" to prod.name,
                        "price" to prod.price,
                        "descripcion" to prod.descripcion,
                        "cantidad" to prod.cantidad,
                        "image" to prod.image
                    )
                }

                val carrito = hashMapOf(
                    "subtotal" to subtotal,
                    "iva" to iva,
                    "total" to total,
                    "productos" to productosFirebase
                )

                database.child("carritos").push().setValue(carrito)

                Toast.makeText(this, "Carrito guardado en Firebase", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, DividirCuentaActivity::class.java))

            } else {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarTotal() {

        val total = CartManager.getTotal()
        val subtotal = total / 1.16
        val iva = total - subtotal

        tvSubtotal.text = "$${String.format("%.2f", subtotal)}"
        tvIVA.text = "$${String.format("%.2f", iva)}"
        tvTotal.text = "$${String.format("%.2f", total)}"
    }

    class AdaptadorCarrito(
        val contexto: Context,
        val productos: ArrayList<Product>,
        val updateCallback: () -> Unit
    ) : BaseAdapter() {

        override fun getCount() = productos.size
        override fun getItem(position: Int) = productos[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val vista = LayoutInflater.from(contexto)
                .inflate(R.layout.item_carrito, parent, false)

            val prod = productos[position]

            val nombre = vista.findViewById<TextView>(R.id.nombre_carrito)
            val precio = vista.findViewById<TextView>(R.id.precio_carrito)
            val imagen = vista.findViewById<ImageView>(R.id.img_carrito)
            val btnEliminar = vista.findViewById<ImageButton>(R.id.btn_eliminar_item)

            nombre.text = prod.name
            precio.text = "$${prod.price}"
            imagen.setImageResource(prod.image)

            btnEliminar.setOnClickListener {
                productos.removeAt(position)
                notifyDataSetChanged()
                updateCallback()
            }

            return vista
        }
    }
}