package com.example.finalproyect2023_2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproyect2023_2.ACTIVITIES.EditarDatosActivity
import com.example.finalproyect2023_2.ACTIVITIES.ListaPostulacionesActivity
import com.example.finalproyect2023_2.ADAPTERS.JobOffersAdapter
import com.example.finalproyect2023_2.ENTITIES.JobOffer
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class JobOffersActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: JobOffersAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_offers)

        viewPager = findViewById(R.id.viewPager)
        firestore = FirebaseFirestore.getInstance()

        val query = firestore.collection("job_offers").orderBy("timestamp", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<JobOffer>()
            .setQuery(query, JobOffer::class.java)
            .build()

        adapter = JobOffersAdapter(options, applicationContext)
        viewPager.adapter = adapter
        //  eventos a las imagenes para redirigir a los demas actovus


        val imageView1: ImageView = findViewById(R.id.imageView5)
        val imageView2: ImageView = findViewById(R.id.imageView6)
        val imageView3: ImageView = findViewById(R.id.imageView3)
        val imageView4: ImageView = findViewById(R.id.imageView4)

        imageView1.setOnClickListener {
            // cierro mi sesion
            signOut();
            finish()
        }

        imageView2.setOnClickListener {
            // Redirige a otra vista cuando se hace clic en imageView2
            val intent = Intent(this, ListaPostulacionesActivity::class.java)
            startActivity(intent)
        }
/*
        imageView3.setOnClickListener {
            // Redirige a otra vista cuando se hace clic en imageView3
            val intent = Intent(this, OtraActividad::class.java)
            startActivity(intent)
        }
*/
        imageView4.setOnClickListener {
            // Redirige a otra vista cuando se hace clic en imageView4
            val intent = Intent(this,  EditarDatosActivity::class.java)
            startActivity(intent)
        }


    }
    private fun signOut() {
        // Obtener la instancia de FirebaseAuth
        val auth = FirebaseAuth.getInstance()

        // Cerrar sesión
        auth.signOut()

        // Realizar cualquier acción adicional después de cerrar sesión
        // Por ejemplo, redirigir a la pantalla de inicio de sesión
        // o mostrar un mensaje de éxito al usuario
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
