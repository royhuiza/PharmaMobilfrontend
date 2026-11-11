package pe.edu.upeu.pharmamobil.domain.repository

data class EstadoSincronizacion(
    val pendientes: Int = 0,
    val fallidas: Int = 0
)