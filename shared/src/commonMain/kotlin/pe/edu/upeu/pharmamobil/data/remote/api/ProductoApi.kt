package pe.edu.upeu.pharmamobil.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.parameter
import pe.edu.upeu.pharmamobil.data.remote.SesionAuth
import pe.edu.upeu.pharmamobil.data.remote.dto.PaginaResponseDto
import pe.edu.upeu.pharmamobil.data.remote.dto.ProductoRequestDto
import pe.edu.upeu.pharmamobil.data.remote.dto.ProductoResponseDto
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class ProductoApi(
    private val client: HttpClient,
    private val urlBase: String,
    private val sesionAuth: SesionAuth
) {
    suspend fun listarProductos(
        pagina: Int = 0,
        tamanio: Int = 20
    ): PaginaResponseDto<ProductoResponseDto> {

        val token = sesionAuth.obtenerToken()
            ?: error("Debes iniciar sesión para consultar el servidor")

        val respuesta = client.get("${urlBase}productos") {
            bearerAuth(token)
            parameter("pagina", pagina)
            parameter("tamanio", tamanio)
        }

        val contenido = respuesta.bodyAsText()

        println("PRODUCTOS GET HTTP: ${respuesta.status}")
        println("PRODUCTOS GET JSON: $contenido")

        if (!respuesta.status.isSuccess()) {
            error("Error HTTP ${respuesta.status} al consultar productos")
        }

        return respuesta.body()
    }

    suspend fun registrarProducto(
        producto: ProductoRequestDto
    ): ProductoResponseDto {

        val token = sesionAuth.obtenerToken()
            ?: error("Debes iniciar sesión para sincronizar productos")

        val respuesta = client.post("${urlBase}productos") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
            setBody(producto)
        }

        val contenido = respuesta.bodyAsText()

        println("PRODUCTOS POST HTTP: ${respuesta.status}")
        println("PRODUCTOS POST JSON: $contenido")

        if (!respuesta.status.isSuccess()) {
            error("Error HTTP ${respuesta.status} al registrar producto")
        }

        return respuesta.body()
    }

}

