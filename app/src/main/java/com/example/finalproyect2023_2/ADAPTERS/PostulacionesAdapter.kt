import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproyect2023_2.ENTITIES.Postulacion
import com.example.finalproyect2023_2.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class PostulacionesAdapter(private val postulacionesList: MutableList<Postulacion>) : RecyclerView.Adapter<PostulacionesAdapter.PostulacionViewHolder>() {

    inner class PostulacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        private val empresaTextView: TextView = itemView.findViewById(R.id.idEmpresaTextView)
        private val eliminarButton: Button = itemView.findViewById(R.id.eliminarButton)

        fun bind(postulacion: Postulacion) {
            nombreTextView.text = postulacion.nombre
            empresaTextView.text = postulacion.detalles

            eliminarButton.setOnClickListener {
                eliminarPostulacion(adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostulacionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_postulacion, parent, false)
        return PostulacionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostulacionViewHolder, position: Int) {
        val postulacion = postulacionesList[position]
        holder.bind(postulacion)
    }

    override fun getItemCount(): Int {
        return postulacionesList.size
    }


    fun eliminarPostulacion(position: Int) {
        if (position in 0 until postulacionesList.size) { // Verificar si la posición es válida
            val postulacionEliminada = postulacionesList.removeAt(position)
            notifyItemRemoved(position)

            // Llamar a la función para eliminar la postulación de Firebase Firestore
            eliminarPostulacionDeFirestore(postulacionEliminada)
        }
    }
    private fun eliminarPostulacionDeFirestore(postulacion: Postulacion) {
        val db = FirebaseFirestore.getInstance()

        // Aquí asumimos que tienes una colección "postulaciones" en tu base de datos de Firebase Firestore
        val postulacionesRef = db.collection("Postulaciones")

        // Eliminar el documento de la postulación correspondiente en Firebase Firestore
        postulacionesRef.document(postulacion.idPostulacion).delete()
            .addOnSuccessListener {
                // La postulación se eliminó correctamente de Firebase Firestore
            }
            .addOnFailureListener { e ->
                // Ocurrió un error al intentar eliminar la postulación de Firebase Firestore
                // Puedes manejar el error según tus necesidades
            }
    }


}
