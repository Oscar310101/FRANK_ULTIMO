package com.example.finalproyect2023_2.ACTIVITIES

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproyect2023_2.ENTITIES.Postulante
import com.example.finalproyect2023_2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditarDatosActivity : AppCompatActivity() {

    private lateinit var nombreTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var nombreEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var btnActualizar: Button

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_datos)

        // Inicializar elementos de la interfaz de usuario
        nombreTextView = findViewById(R.id.textViewNombre)
        emailTextView = findViewById(R.id.textViewEmail)
        nombreEditText = findViewById(R.id.editTextNombre)
        emailEditText = findViewById(R.id.editTextEmail)
        btnActualizar = findViewById(R.id.btnActualizar)

        // Inicializar Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Obtener el usuario actual
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val uid = currentUser.uid

            val postulanteId = uid

            // Obtener los datos del postulante desde Firebase Firestore y mostrarlos en los elementos de la interfaz de usuario
            firestore.collection("Postulante").document(postulanteId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val postulante = documentSnapshot.toObject(Postulante::class.java)
                    nombreTextView.text = "Nombre: ${postulante?.nombre}"
                    emailTextView.text = "Email: ${postulante?.email}"
                    nombreEditText.setText(postulante?.nombre)
                    emailEditText.setText(postulante?.email)
                }
                .addOnFailureListener { e ->
                    // Manejar errores al obtener los datos del postulante
                }

            // Configurar el botón "Actualizar" para guardar los cambios en Firebase Firestore
            btnActualizar.setOnClickListener {
                val nuevoNombre = nombreEditText.text.toString()
                val nuevoEmail = emailEditText.text.toString()

                // Actualizar los datos del postulante en Firebase Firestore
                firestore.collection("Postulante").document(postulanteId)
                    .update(
                        "nombre", nuevoNombre,
                        "email", nuevoEmail
                    )
                    .addOnSuccessListener {
                        // Mostrar un mensaje de éxito o realizar otras acciones necesarias
                    }
                    .addOnFailureListener { e ->
                        // Manejar errores al actualizar los datos del postulante
                    }
            }
        }
    }
}

