package pe.edu.upeu.pharmamobil.platform

enum class Permiso {
    CAMARA,
    UBICACION,
    NOTIFICACIONES
}

enum class EstadoPermiso {
    CONCEDIDO,
    DENEGADO,
    DENEGADO_PARA_SIEMPRE
}

interface GestorPermisos {
    suspend fun consultar(
        permiso: Permiso
    ): EstadoPermiso

    suspend fun solicitar(
        permiso: Permiso
    ): EstadoPermiso
}