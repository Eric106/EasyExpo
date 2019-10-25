package mx.keri.easyexpo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info_museo.*
import kotlinx.android.synthetic.main.activity_info_obra.*
import kotlinx.android.synthetic.main.activity_museos.*

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
    }

    fun updateInfo(){

    }
}
