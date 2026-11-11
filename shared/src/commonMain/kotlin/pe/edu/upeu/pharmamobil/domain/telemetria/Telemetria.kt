package pe.edu.upeu.pharmamobil.domain.telemetria

interface Telemetria {
    fun registrarEvento(
        nombre: String,
        parametros: Map<String, String> = emptyMap()
    )
}
