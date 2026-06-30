import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calzado.R

class CalzadoEliminarAdapter(
    private val lista: MutableList<Calzado> // Cambiado a MutableList para poder modificarla
) : RecyclerView.Adapter<CalzadoEliminarAdapter.ViewHolderClass>() {

    // Lista interna para recordar cuáles calzados están seleccionados por el usuario
    val itemsSeleccionados = mutableListOf<Calzado>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_holder_eliminar, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val item = lista[position] // Obtenemos el elemento

        holder.nombre.text = "Nombre: " + item.nombre
        holder.tipo.text = "Tipo: " + item.tipoCalzado
        holder.modelo.text = "Modelo: " + item.modelo

        // Limpiamos cualquier listener previo para evitar errores con el reciclaje de vistas
        holder.checkBox.setOnCheckedChangeListener(null)

        // Seteamos si debe estar marcado o no basándonos en nuestra lista interna de seleccionados
        holder.checkBox.isChecked = itemsSeleccionados.contains(item)

        // Escuchamos cuando el usuario marca o desmarca
        holder.checkBox.setOnCheckedChangeListener { _, marcado ->
            if (marcado) {
                if (!itemsSeleccionados.contains(item)) {
                    itemsSeleccionados.add(item)
                }
            } else {
                itemsSeleccionados.remove(item)
            }
        }
    }

    override fun getItemCount(): Int = lista.size

    // Esta es la función que limpia los datos de forma segura
    fun eliminarSeleccionados(): Boolean {
        if (itemsSeleccionados.isEmpty()) {
            return false // No había nada seleccionado
        }

        lista.removeAll(itemsSeleccionados) // Los quita de ListaCalzado.Lista
        itemsSeleccionados.clear()          // Limpia la lista de seleccionados
        notifyDataSetChanged()              // Redibuja el RecyclerView en tiempo real
        return true // Eliminación exitosa
    }

    class ViewHolderClass(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox = view.findViewById<CheckBox>(R.id.chkCalzado)
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val tipo = view.findViewById<TextView>(R.id.txtTipo)
        val modelo = view.findViewById<TextView>(R.id.txtModelo)
    }
}
