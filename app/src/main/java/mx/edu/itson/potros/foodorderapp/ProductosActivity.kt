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
                menu.add(Product("Quesadillas", R.drawable.quesadillas, "Rellenas con su carne favorita...", 6.29, 0))
                menu.add(Product("Huaraches", R.drawable.huaraches, "Tortilla gruesa con frijoles...", 11.49, 0))
                menu.add(Product("Gringas", R.drawable.gringas, "Tortilla de harina con queso...", 8.39, 0))
                menu.add(Product("Mojarra frita", R.drawable.mojarra, "Tilapia frita...", 17.99, 0))
                menu.add(Product("Ceviche", R.drawable.ceviche, "Tilapia, camarones...", 6.99, 0))
                menu.add(Product("Burrito Pepe", R.drawable.burritopepe, "Flour tortilla...", 10.99, 0))
                menu.add(Product("Torta cubana", R.drawable.tortacubana, "Chorizo, asada...", 14.49, 0))
                menu.add(Product("Caldo de res", R.drawable.caldores, "Beef ribs stew...", 8.39, 0))
                menu.add(Product("Pozole", R.drawable.pozole, "Pork ribs stew...", 7.99, 0))
            }

            "Postres" -> {
                menu.add(Product("Flan", R.drawable.flan, "Postre tradicional de caramelo.", 3.99, 0))
                menu.add(Product("Pastel de chocolate", R.drawable.pastel, "Rebanada de pastel de chocolate.", 4.99, 0))
                menu.add(Product("Churros", R.drawable.churros, "Churros con azúcar y canela.", 3.49, 0))
            }

            "Bebidas" -> {
                menu.add(Product("Sodas", R.drawable.sodas, "Sodas 20 oz.", 2.99, 0))
                menu.add(Product("Jarritos", R.drawable.jarritos, "Refresco mexicano.", 2.75, 0))
                menu.add(Product("Agua fresca", R.drawable.aguafresca, "Bebida natural de frutas.", 2.50, 0))
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