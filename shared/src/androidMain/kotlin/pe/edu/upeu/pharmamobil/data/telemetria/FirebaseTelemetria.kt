package pe.edu.upeu.pharmamobil.data.telemetria

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import pe.edu.upeu.pharmamobil.domain.telemetria.Telemetria

class FirebaseTelemetria(
    private val analytics: FirebaseAnalytics
) : Telemetria {

    override fun registrarEvento(
        nombre: String,
        parametros: Map<String, String>
    ) {
        val bundle = Bundle()

        parametros.forEach { (clave, valor) ->
            bundle.putString(clave, valor)
        }

        analytics.logEvent(nombre, bundle)
    }
}