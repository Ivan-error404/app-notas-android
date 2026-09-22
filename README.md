# Mis Notas — App Android (Java)

Aplicación Android de **notas personales** desarrollada en **Java** con **SQLite**, Material Design 3 y RecyclerView. Incluye registro e inicio de sesión de usuarios.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)

## Características

- **Registro e inicio de sesión** de usuarios con base de datos SQLite
- **CRUD de notas**: crear, listar y eliminar
- Interfaz con **Material Design 3** (MaterialToolbar, FAB, CardView)
- **Tema claro y oscuro** automático
- Notas guardadas por usuario

## Estructura del proyecto

```
app/
├── build.gradle
└── src/main/
    ├── AndroidManifest.xml
    ├── java/com/ivan/appnotas/
    │   ├── DatabaseHelper.java   # Capa de datos SQLite
    │   ├── LoginActivity.java    # Pantalla de acceso
    │   ├── RegistroActivity.java # Alta de usuarios
    │   ├── MainActivity.java     # Lista de notas
    │   ├── NotaAdapter.java      # RecyclerView adapter
    │   └── Nota.java             # Modelo
    └── res/
        ├── layout/               # Vistas XML
        └── values/               # Temas, colores, textos
```

## Requisitos

- Android Studio Iguana (2023.2.1) o superior
- JDK 17
- Mínimo SDK 24 (Android 7.0)

## Cómo ejecutar

1. Abre el proyecto en **Android Studio**
2. Espera a que Gradle sincronice las dependencias
3. Pulsa **Run ▶** sobre un emulador o dispositivo físico

Primer uso: crea una cuenta desde la pantalla de login.

## Base de datos

| Tabla      | Campos                                   |
|------------|------------------------------------------|
| `usuarios` | id, usuario, password                    |
| `notas`    | id, usuario, titulo, contenido, fecha    |