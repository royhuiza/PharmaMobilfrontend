
package pe.edu.upeu.pharmamobil.di

import org.koin.android.ext.koin.androidContext
import pe.edu.upeu.pharmamobil.data.local.CreadorDeDriver
import pe.edu.upeu.pharmamobil.data.local.CreadorDeDriverAndroid
import pe.edu.upeu.pharmamobil.platform.CompartirProducto
import pe.edu.upeu.pharmamobil.platform.CompartirProductoAndroid
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.google.firebase.analytics.FirebaseAnalytics
import pe.edu.upeu.pharmamobil.data.telemetria.FirebaseTelemetria
import pe.edu.upeu.pharmamobil.domain.telemetria.Telemetria

actual val platformModule: Module = module {

    single<FirebaseAnalytics> {
        FirebaseAnalytics.getInstance(androidContext())
    }

    single<Telemetria> {
        FirebaseTelemetria(get())
    }

    // Base de datos SQLite
    single<CreadorDeDriver> {
        CreadorDeDriverAndroid(androidContext())
    }

    // Motor de conexión HTTP
    single<HttpClientEngine> {
        OkHttp.create()
    }

    // Dirección del backend PharmaSoft
    single(named("urlBase")) {
        "https://pharmasoft-backend-deploy.onrender.com/api/v1/"
    }

    // Compartir mediante el menú nativo de Android
    single<CompartirProducto> {
        CompartirProductoAndroid(androidContext())
    }
}
