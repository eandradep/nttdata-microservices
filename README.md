# nttdata-microservices

`nttdata-microservices` es un proyecto que utiliza micro servicios para la orquestación de tareas.

## Descripción del Proyecto

Aquí tendrías que explicar más a fondo en qué consiste el proyecto. ¿Qué problemas resuelve? ¿Cómo se estructura el proyecto?

## Requisitos

- Java JDK 17
- Maven
- Docker

## Cómo empezar

### Instalación

Para empezar, necesitas instalar todos los requisitos mencionados en la sección anterior en tu sistema. Cuando estén correctamente instalados, puedes clonar este repositorio a una carpeta local:

Ejecutar el archivo [deployDevEnvironment.yml](deployDevEnvironment.yml) usando docker compose.

- sudo docker compose -f deployService.yml up --build --force-recreate -d
- Ejecutar el proyecto [nttdata-eureka-server](nttdata-eureka-server)
- Ejecutar el proyecto [nttt-data-client-service](nttt-data-client-service)
- Ejecutar el proyecto [ntt-data-account-service](ntt-data-account-service)

Las configuraciones de conexión con la base de datos y demás, están centralizadas en nuestro servidor de configuraciones. Este se encuentra en el archivo. [deployDevEnvironment.yml](deployDevEnvironment.yml)

El archivo [NTT-DATA.postman_collection.json](NTT-DATA.postman_collection.json) contiene la configuration en postman para poder consumir los servicios.

### Pasos Completados.

- Centralizar configuration de microservicios en un unico servidor.
- implementar communication entre microservicios mediante Feing.
- Crud de entidades.
- Dockerizado ded base dee datos y servidor de configuration.
- Puntos F2 Y F3.
- Creation de un proyecto commons para almacenar estandarización de mensajes y colocación de entidades.

### Pasos pendientes.

- Completar el servicio para obtener el reporte.
- Configurar Servidor gateway para consumir todos los servicios desde una misma url base.
- Pruebas unitarias.
- Generar contenedores para desplegar servidor Eureka, microservicios, Servidor gateway y servidor Zipkin para monitoreo de paquetes.
- Generar pruebas unitarias.
- En el modo pre production, no se requiere la colocacion de puertos, ya que estos son asignados de manera aleatoria, permitiendo tener varios microservicios similares trabajando en diferentes puertos.
- 