package pe.edu.upeu.pharmamobil

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val preferencias = getSharedPreferences("permisos", MODE_PRIVATE)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            var mostrarPermisos by remember {
                mutableStateOf(false)
            }

            var estadoCamara by remember {
                mutableStateOf("No solicitado")
            }

            val launcherCamara = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { concedido ->

                estadoCamara = when {
                    concedido -> "CONCEDIDO"

                    !shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA
                    ) -> "DENEGADO_PARA_SIEMPRE"

                    else -> "DENEGADO"
                }

                preferencias.edit()
                    .putBoolean("camara_solicitada", true)
                    .apply()
            }

            if (mostrarPermisos) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Permisos de PharmaMobil")
                    Text("Cámara: $estadoCamara")

                    Button(
                        onClick = {
                            val permiso = Manifest.permission.CAMERA

                            val concedido = ContextCompat.checkSelfPermission(
                                this@MainActivity,
                                permiso
                            ) == PackageManager.PERMISSION_GRANTED

                            val solicitadoAntes = preferencias.getBoolean(
                                "camara_solicitada",
                                false
                            )

                            when {
                                concedido -> estadoCamara = "CONCEDIDO"

                                solicitadoAntes &&
                                        !shouldShowRequestPermissionRationale(permiso) -> {
                                    estadoCamara = "DENEGADO_PARA_SIEMPRE"
                                }

                                else -> launcherCamara.launch(permiso)
                            }
                        }
                    ) {
                        Text("Solicitar cámara")
                    }

                    Button(onClick = { mostrarPermisos = false }) {
                        Text("Volver a PharmaMobil")
                    }
                }
            } else {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}