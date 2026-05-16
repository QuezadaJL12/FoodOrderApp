package mx.edu.itson.potros.foodorderapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {
    // Instancias de Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val btnEnviar = findViewById<Button>(R.id.btnEnviar)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
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


                val textoFinal = if (textoConFormato.length > 10) textoConFormato.substring(
                    0,
                    10
                ) else textoConFormato.toString()

                etFechaNacimiento.setText(textoFinal)
                etFechaNacimiento.setSelection(textoFinal.length)

                isFormatting = false
            }
        })

        btnEnviar.setOnClickListener {

            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val contra = etPassword.text.toString().trim()
            val fecha = etFechaNacimiento.text.toString().trim()

            // Validaciones

            if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty() || telefono.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Nombre solo letras
            if (!nombre.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$"))) {
                Toast.makeText(this, "El nombre solo debe contener letras", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Correo válido
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Contraseña mínima
            if (contra.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Teléfono solo números y 10 dígitos
            if (!telefono.matches(Regex("^[0-9]{10}$"))) {
                Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Fecha básica (dd/MM/yyyy)
            if (!fecha.matches(Regex("^\\d{2}/\\d{2}/\\d{4}$"))) {
                Toast.makeText(this, "Formato de fecha inválido (dd/MM/yyyy)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear usuario en Firebase Authentication
            auth.createUserWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val userId = auth.currentUser?.uid
                        val nuevoUsuario = Usuario(0, nombre, fecha, correo, telefono, contra)

                        if (userId != null) {
                            database.reference.child("usuarios").child(userId)
                                .setValue(nuevoUsuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Error al guardar datos: ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }

                    } else {
                        Toast.makeText(
                            this,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}