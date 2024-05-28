# Como usar
La aplicación escucha en el puerto 8080.

- Los endpoints ubicados en /api/v1/noAuth no requieren autenticación.
- Los enpoints ubicados en /api/v1/user requieren estar autenticado con un usuario cualquiera
- Los enpoints ubicados en /api/v1/admin requieren estar autenticado con un usuario administrador

---
## Autenticación

Para crear un usuario, se usa el endpoint POST "/api/v1/noAuth/register" y se pasan los datos por el cuerpo de la peticion, por ejemplo:
```json
{
  "name": "admin",
  "email": "admin@gmail.com",
  "password": "contraAdmin"
}
```
Para crear un usuario administrador, se usa en su lugar el endpoint POST "/api/v1/admin/registerAdmin", que requiere un usuario administrador.

Si el registro ha tenido éxito, se devolverá un token de autenticación. Para obtener otro token, se usa el endpoint GET "/api/v1/noAuth/authenticate"
y se pasan las credenciales por el cuerpo, por ejemplo:
```json
{
  "email": "test@gmail.com",
  "password": "contraseña"
}
```
Para autenticarse, hay que proporcionar el token junto con la petición usando el prefijo "Bearer". Por ejemplo, usando Thunder Client:
![image](https://github.com/ALVSanchez/muchofiesta-api/assets/94567015/811276ac-66c9-4a72-88c8-ad229fe9dffb)

## Inicializar pruebas
La aplicación no tiene pruebas al principio. Para añadirlas, se usa el endpoint POST "/api/v1/admin/initChallenges" proporcionando un array de pruebas en el cuerpo.
Ésto también borrará las pruebas que existiesen anteriormente.
[Éste archivo](src/main/resources/static/init/challenges.json) contiene las pruebas iniciales.

Si se quieren añadir pruebas nuevas, se puede usar "/api/v1/admin/postChallenge", que admite una sola prueba en el cuerpo.

Para obtener todas las pruebas, se usa el endpoint GET "/api/v1/noAuth/getChallenges".

## Imágenes

- /api/v1/user/uploadImage
    Sube una imágen. Ejemplo de Thunder Client: ![image](https://github.com/ALVSanchez/muchofiesta-api/assets/94567015/6918b27d-7d94-4271-b7ed-e28fd09bb632)
- /api/v1/user/getImage/{id}
    Obtiene una imágen. El usuario autenticado debe ser el mismo que subió la imagen.
