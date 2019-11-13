package mx.keri.easyexpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_info_museo.*

class infoMuseo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_museo)
        var museito = intent.getStringExtra("Museo")

        btnMuseo.setOnClickListener { view ->
            val intConsulta = Intent(baseContext, infoObra::class.java)
                .putExtra("Museo",museito)
            startActivity(intConsulta)
        }


        val wal = FirebaseDatabase.getInstance().getReference("/Museos/${museito}/")
        wal.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(w0: DataSnapshot) {
                if (w0.exists()){
                    val info_museo = w0.getValue(info::class.java)

                    if (info_museo!= null){
                        Log.d("Why",info_museo.toString())

                        val tv = tvMuseo
                        tv.text = info_museo?.descripcion.toString()
                    }
                } else{
                    Log.d("Why","No existe")
                }


            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

    class info(var nombre:String?,var descripcion:String?){
        constructor():this("","")
    }
}

