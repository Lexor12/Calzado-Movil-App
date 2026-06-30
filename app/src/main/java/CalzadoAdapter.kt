import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calzado.R
import com.example.calzado.tarjeta_calzado

class CalzadoAdapter(
    private val Lista: List<Calzado>//Definimos nuestros atributos de clase // **1.** (Orden de lectura para entender)
) : RecyclerView.Adapter<CalzadoAdapter.ViewHolderClass>(){
    override fun onCreateViewHolder(//Se llama cuando necesitamos crear una tarjeta
        parent: ViewGroup,//Padre
        viewType: Int//Tipo de vista
    ): ViewHolderClass {//Devuelve un objeto que hace referencia a un elemento nuevo, y este se acumula
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_holder_ver,parent,false)//Creamos un nuevo elemento tanto visible, como carga aspectos
        return ViewHolderClass(view)//Aqui lo devolvemos en el formato correcto, ya que creamos un objeto de tipo ViewHolderClass, el cual ya agarra la vista y hace sus equivalencias de cada elemento y ya como aqui en memoria para manipular despues
    }
    // **4.**
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {//Funcion que se ejecuta cada que mostramos un elemento
        val item=Lista[position]//Pasamos la posicion explicita que estamos a punto de mostrar
        holder.nombre.text=item.nombre//holder es un objeto que se crea que tiene las propiedades de la referencia al lugar donde lo creamos
        holder.nombre.setOnClickListener {
            //Pensariamos en una activity, pero no podemos poner this, ya que no estamos en el contexto de una actividad, por esto, debemos colocar algo llamado contexto
            val context = holder.itemView.context//Trabajaremos en el contexto de los items ya que ellos se encuentran en una actividad
            val tar = Intent(context, tarjeta_calzado::class.java)
            tar.putExtra("position",position)
            //startActivity(tar), necesitamos usar el contexto
            context.startActivity(tar)
        }
        holder.marca.text=item.marca//Aqui simplemente modificamos los elementos visuales del contenedor ya dicho
        //Aqui decidimos que cosas SI mostrar y que cosas NO
    }
    // **2.**
    override fun getItemCount(): Int=Lista.size//Funcion que devuelve cantidad de valores del recyclerview, fundamental para calculo de cargar nuevos elementos
    // **3.**
    class  ViewHolderClass(view: View): RecyclerView.ViewHolder(view){//Clase dentro de una clase, la cual, permite instanciar un objeto, con atriburosd e nomre y marca
    //Los cuales son equivalentes a 1 elemento especifico de nuestro recycler view
        val nombre = view.findViewById<TextView>(R.id.txtNombre)
        val marca = view.findViewById<TextView>(R.id.txtMarca)
    }
}