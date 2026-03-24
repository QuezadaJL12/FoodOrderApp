package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)


        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)


        etFechaNacimiento.addTextChangedListener(object : TextWatcher {
            var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting) return
                isFormatting = true


                val numerosLimpios = s.toString().replace(Regex("[^\\d]"), "")
                val textoConFormato = StringBuilder()


                for (i in numerosLimpios.indices) {
                    if (i == 2 || i == 4) {
                        textoConFormato.append("/")
                    }
                    textoConFormato.append(numerosLimpios[i])
                }


                val textoFinal = if (textoConFormato.length > 10) textoConFormato.substring(0, 10) else textoConFormato.toString()

                etFechaNacimiento.setText(textoFinal)
                etFechaNacimiento.setSelection(textoFinal.length)

                isFormatting = false
            }
        })

        btnEnviar.setOnClickListener {
            val correoStr = etCorreo.text.toString()
            val passStr = etPassword.text.toString()


            if (correoStr.isEmpty() || passStr.isEmpty()) {
                Toast.makeText(this, "Por favor llena correo y contraseña", Toast.LENGTH_SHORT).show()
            } else {

                val sharedPreferences = getSharedPreferences("MisDatos", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("correoGuardado", correoStr)
                editor.putString("passwordGuardada", passStr)
                editor.apply() // Guarda los cambios

                Toast.makeText(this, "¡Registro Exitoso!", Toast.LENGTH_SHORT).show()
                finish() // Cierra esta pantalla y regresa a la principal
            }
        }

    }
}