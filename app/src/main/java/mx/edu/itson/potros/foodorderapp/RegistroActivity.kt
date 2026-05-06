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
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()
            val telefono = etTelefono.text.toString()
            val contra = etPassword.text.toString()
            val fecha = etFechaNacimiento.text.toString()

            if (nombre.isEmpty() || correo.isEmpty() || contra.isEmpty()) {
                Toast.makeText(this, "Por favor llena los campos obligatorios", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Crear usuario en Firebase Authentication
            auth.createUserWithEmailAndPassword(correo, contra)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //Si se creó bien, guardamos los datos extra en Realtime Database
                        val userId = auth.currentUser?.uid
                        val nuevoUsuario = Usuario(0, nombre, fecha, correo, telefono, contra)

                        if (userId != null) {
                            database.reference.child("usuarios").child(userId)
                                .setValue(nuevoUsuario)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT)
                                        .show()
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