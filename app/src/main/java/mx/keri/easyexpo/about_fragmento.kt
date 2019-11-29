package mx.keri.easyexpo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragmento_about.*
import mx.keri.easyexpo.About
import mx.keri.easyexpo.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class about_fragmento : Fragment() {

    //indice objeto que se muestra
    var indice = 0
        set(value) {
            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_about, container, false)
    }

    override fun onStart() {
        super.onStart()

        if(view != null) {
            val info = About.arrInfo[indice]
            tvDetalle.text = info.nombre
        }
    }

}
