package mx.keri.easyexpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info_museo.*

class infoMuseo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_museo)
        var museito = intent.getStringExtra("Museo")
        var TAG = "_DEBUG"
        btnMuseo.setOnClickListener { view ->
            val intConsulta = Intent(baseContext, infoObra::class.java)
                .putExtra("Museo",museito)
            startActivity(intConsulta)
        }

        var tv = tvMuseo
        var tvTitulo = tvTitle
        var imv = ivMuseo
        val wal = FirebaseDatabase.getInstance().getReference("/Museos/${museito}")
        wal.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(w0: DataSnapshot) {
                if (w0.exists()){
                    Log.d(TAG, "El museo exite.")
                    val info_museo  = w0.getValue(Info::class.java)

                    Log.d(TAG,"InfoMuseoNombre: ${info_museo?.Nombre} e InfoMuseoDescripción ${info_museo?.Descripcion}")
                    if (info_museo!= null){
                        Log.d("Why",info_museo.toString())
                        tv.text = info_museo?.Descripcion.toString()
                        tv.movementMethod = ScrollingMovementMethod()
                        tvTitulo.text = info_museo?.Nombre.toString()
                        Picasso.get().load(info_museo?.Imagen).into(imv)
                    }
                } else{
                    Log.d(TAG,"No existe")
                }


            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

}

