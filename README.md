# BurrosU2

Este proyecto gestiona competencias y apuestas de burros utilizando el patrón de diseño **MVC (Modelo-Vista-Controlador)**. Permite registrar competencias, burros y participantes, realizar apuestas y calcular ganancias.

## Características

- Registro de competencias.
- Gestión de burros participantes.
- Asignación de puestos en competencias.
- Realización de apuestas por participantes.
- Listado de ganadores de competencias.
- Cálculo de ganancias por apuestas (solo considerando apuestas perdedoras).

## Estructura del Proyecto

### Modelo
Las clases del modelo representan los datos y la lógica empresarial del proyecto. Estas clases incluyen:

- **Participante**: Representa a un participante en las competencias y apuestas.
- **Competencia**: Representa una competencia con sus detalles y burros participantes.
- **Burro**: Representa a un burro participante en las competencias.

### Persistencia
La capa de persistencia se encarga de la interacción con la base de datos. Incluye:

- **CRUD.java**: Contiene métodos estáticos para realizar operaciones de Crear, Leer, Actualizar y Eliminar (CRUD) en la base de datos.
- **BdConexion.java**: Maneja la conexión a la base de datos.

### Controladores
Los controladores gestionan la lógica de negocio y actúan como intermediarios entre el modelo y las vistas:

- **ParticipanteControlador**: Gestiona la lógica relacionada con los participantes.
- **CompetenciaControlador**: Gestiona la lógica relacionada con las competencias.
- **BurroControlador**: Gestiona la lógica relacionada con los burros.
- **ApuestasControlador**: Gestiona la lógica relacionada con las apuestas.

### Vistas
Las vistas interactúan con el usuario y muestran los datos. Las vistas incluyen menús para interactuar con el sistema:

- **ParticipantesMenu.java**: Permite registrar, actualizar, eliminar y listar participantes.
- **CompetenciasMenu.java**: Permite gestionar competencias, asignar puestos y listar ganadores.
- **BurroMenu.java**: Permite gestionar burros (registrar, actualizar, eliminar).
- **ApuestasMenu.java**: Permite realizar y listar apuestas, y calcular ganancias.

## Requisitos

- Java Development Kit (JDK) 8 o superior.
- Base de datos compatible (Ej. MySQL, PostgreSQL).
- Librería JDBC para la base de datos utilizada.

## Instalación

1. Clona este repositorio.

```bash
git clone https://github.com/tu-usuario/proyecto-competencias-apuestas.git

```
Configura la conexión a la base de datos en BdConexion.java.
```
    private static final String url = "jdbc:mysql://localhost:3306/BurrosU2";
    private static final String user = "root";
    private static final String password = "J12345";
```
