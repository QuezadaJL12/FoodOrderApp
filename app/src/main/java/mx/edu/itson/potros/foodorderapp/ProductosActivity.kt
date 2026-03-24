package mx.edu.itson.potros.foodorderapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.ArrayList;

class ProductosActivity : AppCompatActivity() {

    var menu:ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productos)

        val menuOption: String? = intent.getStringExtra("menuType")
        agregarProductos(menuOption)

        val imagen: ImageView = findViewById(R.id.imageView)

        when(menuOption){
            "Platillos" -> imagen.setImageResource(R.drawable.platillos)
            "Postres" -> imagen.setImageResource(R.drawable.postres)
            "Bebidas" -> imagen.setImageResource(R.drawable.bebidas)
        }

        val listView: ListView = findViewById(R.id.listView)
        val adaptador = AdaptadorProductos(this, menu)
        listView.adapter = adaptador

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun agregarProductos(option: String?){
        when(option){

            "Platillos" -> {
                menu.add(Product("Quesadillas", R.drawable.quesadilla, "Tortilla con queso adentro (Escoja si quiere tortilla de maíz o harina)", 20.99, 8))
                menu.add(Product("Huaraches", R.drawable.huarache, "Tortilla gruesa con frijoles", 60.99, 10))
                menu.add(Product("Hamburguesa", R.drawable.hamburguesa, "Hay opción de carne de res, pollo o incluso vegana", 150.99, 12))
                menu.add(Product("Sushi", R.drawable.sushi, "Platillo japonés tradicional.", 119.99, 11))
                menu.add(Product("Ceviche", R.drawable.ceviche, "Pida a su gusto, con tilapia, camarón o pescado", 57.99, 14))
                menu.add(Product("Burrito frijol y queso", R.drawable.burrito, "Burrito con frijol y queso", 20.99, 7))
                menu.add(Product("Torta cubana", R.drawable.tortacubana, "Pidalo con chorizo, asada o cabeza", 72.99, 9))
            }

            "Postres" -> {
                menu.add(Product("Flan", R.drawable.flan, "Postre tradicional de caramelo.", 25.99, 19))
                menu.add(Product("Pastel de chocolate", R.drawable.pastel, "Pastel de chocolate pequeño", 56.99, 15))
            }

            "Bebidas" -> {
                menu.add(Product("Coca-Cola", R.drawable.coca, "20 oz.", 14.99, 15))
                menu.add(Product("Pepsi", R.drawable.pepsi, "20 oz.", 13.95, 14))
                menu.add(Product("Agua fresca", R.drawable.aguafresca, "Bebida natural de frutas.", 20.99, 50))
            }
        }
    }

    private class AdaptadorProductos(contexto: Context, producto: ArrayList<Product>) : BaseAdapter() {

        var producto: ArrayList<Product> = producto
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