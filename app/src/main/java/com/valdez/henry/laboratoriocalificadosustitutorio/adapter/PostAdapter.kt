package com.valdez.henry.laboratoriocalificadosustitutorio.adapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.valdez.henry.laboratoriocalificadosustitutorio.databinding.ItemPostBinding
import com.valdez.henry.laboratoriocalificadosustitutorio.model.Post
class PostAdapter(
    private val context: Context,
    private val posts: List<Post>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.tvTitle.text = post.title
        holder.binding.tvBody.text = post.body

        // Click corto: enviar título por mensaje SMS
        holder.binding.root.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:+51925137361")
                putExtra("sms_body", post.title) // Enviar título en el mensaje
            }
            context.startActivity(intent)
        }

        // Click largo: enviar body del post por correo
        holder.binding.root.setOnLongClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                // Si deseas limitarlo a una dirección de correo, puedes usar "mailto"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("victor.saico@tecsup.edu.pe"))
                putExtra(Intent.EXTRA_SUBJECT, "Enunciado del post")
                putExtra(Intent.EXTRA_TEXT, post.body)

                // Puedes agregar esto si quieres que se abra directamente la aplicación de correo predeterminada
                type = "message/rfc822"  // Esto es para que solo se muestren las aplicaciones que pueden manejar correos electrónicos.
            }

            // Si no resuelve la actividad, significa que no hay ninguna aplicación de correo instalada.
            if (emailIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(emailIntent)
            } else {
                // Si no hay ninguna aplicación de correo electrónico disponible
                Toast.makeText(context, "No hay aplicaciones de correo electrónico disponibles", Toast.LENGTH_SHORT).show()
            }

            true
        }
    }

    override fun getItemCount(): Int = posts.size
}
