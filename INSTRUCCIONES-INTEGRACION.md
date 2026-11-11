# PharmaMobil1 - frontend preparado para PharmaSoft JWT

## Integración
- El login ya hace POST `${urlBase}auth/login` con `username` y `password`.
- El backend corregido responde con `token`, `tipo`, `expiraEnSegundos`, `username`, `nombreCompleto`, `roles` y `permisos`.
- Productos y categorías envían `Authorization: Bearer <token>`.
- `BackendConfig.kt` centraliza la URL del backend para Android e iOS.

## Prueba local
1. Arranca el backend del ZIP `PharmaSoft_Backend_JWT_Deploy.zip`, configura `DB_PASSWORD`, `JWT_SECRET`, `ADMIN_USERNAME`, `ADMIN_PASSWORD` según sus instrucciones.
2. Comprueba el endpoint `/api/v1/auth/login` mediante Postman antes de abrir la app.
3. Asegura que `BackendConfig.URL_BASE` apunta a la IP accesible desde el teléfono, con `/api/v1/` final.
4. Abre el proyecto en Android Studio, sincroniza Gradle y ejecuta en tu Samsung.

## Servidor público
1. Despliega el backend y PostgreSQL detrás de HTTPS con dominio real.
2. Cambia `BackendConfig.URL_BASE` a `https://TU_DOMINIO/api/v1/`.
3. Compila de nuevo el APK/AAB release y firma con tu clave privada local. **No compartas ni incluyas el archivo JKS o las contraseñas de firma en el ZIP.**
4. En producción, configura `android:usesCleartextTraffic="false"` y retira las excepciones HTTP de `network_security_config.xml` si ya no necesitas acceso HTTP local.
5. Si usas Firebase, restaura tu `androidApp/google-services.json` localmente; se excluyó del ZIP compartible.

## Limitaciones
- No se ha compilado el proyecto Android en este entorno ni se ha probado el login contra un servidor activo.
- La URL pública todavía no existe en la información proporcionada; es obligatorio configurarla antes de publicar el APK.
- `SesionAuth` conserva el token solo en memoria; al cerrar completamente la app hay que iniciar sesión de nuevo.
- La configuración de firma y `local.properties` se excluyeron intencionalmente: deben permanecer en tu computadora.
