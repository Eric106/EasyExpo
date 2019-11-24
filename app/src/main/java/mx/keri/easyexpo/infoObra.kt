package mx.keri.easyexpo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_info_museo.*
import kotlinx.android.synthetic.main.activity_info_obra.*
import java.util.*
import kotlin.collections.ArrayList

class infoObra : AppCompatActivity() {

    private var admBeacons: BeaconManager? = null
    private var region: BeaconRegion? = null

    private var arrBeacons: ArrayList<String>? = null
    private var arrRSSI: ArrayList<String>? = null

    var closestRSSI: String? = null
    var prevMajor:String? = null
    var closestMajor: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_obra)

        admBeacons = BeaconManager(baseContext)
        region = BeaconRegion("MisBeaconCEM", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null)

        admBeacons?.setRangingListener { beaconRegion: BeaconRegion?, beacons: MutableList<Beacon>? ->
            println("-----------------------${beacons.toString()}")
            if (beacons != null) {
                if (arrBeacons == null) {
                    arrBeacons = ArrayList<String>()
                }
                arrBeacons?.clear()
                val iterador = beacons.iterator()
                for (beacon: Beacon? in iterador) {
                    println("${beacon.toString()}")
                    arrBeacons?.add("${beacon?.major}")
                    arrRSSI?.add("${beacon?.major}")

                }

                closestMajor = arrBeacons?.get(0)

                Log.d("Closest",closestMajor)
                actualizarDatos(closestMajor)

                closestRSSI = arrRSSI?.get(0)
            }
        }

        btnObra.setOnClickListener { view ->

            SystemRequirementsChecker.checkWithDefaultDialogs(this)

            val adm = admBeacons
            admBeacons?.connect {
                adm?.startRanging(region)
            }
        }
    }

    private fun actualizarDatos(nuevoTexto:String?) {
        var museito = intent.getStringExtra("Museo")
        Log.d("Why",museito)
        Log.d("Why", nuevoTexto)

        var tvO = tvObra
        var tvTituloO = tvTitutloObra
        var imvO = ivObra
        val wal = FirebaseDatabase.getInstance().getReference("/${museito}/${nuevoTexto}")
        wal.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(w0: DataSnapshot) {
                if (w0.exists()){
                    val info_museo = w0.getValue(info::class.java)
                    val info_obra = w0.getValue(Info::class.java)

                    if (info_museo!= null){
                        Log.d("Why",info_museo.toString())

                        val tv = tvMuseo
                        tv.text = info_museo?.descripcion

                        tvO.text = info_obra?.Descripcion.toString()
                        tvO.movementMethod = ScrollingMovementMethod()
                        tvTituloO.text = info_obra?.Nombre.toString()
                        Picasso.get().load(info_obra?.Imagen).into(imvO)
                    }
                } else{
                    Log.d("Why","No existe")
                }


            }


            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        var tv = tvObra
        tv.text = nuevoTexto
    }

    class info(var nombre:String?,var descripcion:String?){
        constructor():this("","")
    }
}

