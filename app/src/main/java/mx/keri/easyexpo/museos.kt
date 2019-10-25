package mx.keri.easyexpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_museos.*

class museos : AppCompatActivity() {
    lateinit var arrMuseos: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museos)

        arrMuseos = mutableListOf()

        lstMuseos.setOnItemClickListener {adapterView, view, index, id ->

            val nombreMuseo = arrMuseos?.get(index)

            val intObras = Intent(this, infoMuseo::class.java)
            intObras.putExtra("Museo", nombreMuseo)
            startActivity(intObras)
        }
    }

    override fun onStart() {
        super.onStart()
        leerMuseos()
    }

    fun leerMuseos() {
        val baseDatos
                = FirebaseDatabase.getInstance()
        val referencia =baseDatos.getReference("/Museos")

        referencia.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrMuseos.clear()
                for(registro in snapshot.children) {

                    val valor = registro.value as Map<String, String>
                    var nombre = valor.getValue("Nombre")
                    //var museo = registro.getValue(Museo::class.java)
                    Log.d("MUSEOSZ", arrMuseos.toString())
                    arrMuseos.add(nombre)
                }

                Log.d("MUSEOS", arrMuseos.toString())

                val adaptador = ArrayAdapter<String>(baseContext,android.R.layout.simple_list_item_1, arrMuseos)
                lstMuseos.adapter = adaptador
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "Error al cargar los datos", Toast.LENGTH_LONG).show()

            }
        })
    }

    fun pantallaAbout(v: View){
        val intAbout = Intent (this, About::class.java)
        startActivity(intAbout)
    }
}
