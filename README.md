# App de promociones v2 – Centros Comerciales

## Descripción del Proyecto
Este proyecto consiste en el desarrollo de una aplicación móvil que permite a los clientes de un centro comercial registrar facturas de compras realizadas en los locales participantes de una promoción. Por cada monto acumulado (configurable) el cliente recibe oportunidades para participar en un sorteo al final de la promoción.

## Exposición del Problema
Actualmente, muchos centros comerciales realizan sorteos promocionales pero dependen de procesos manuales para receptar las facturas. Esto dificulta la validación, seguimiento y transparencia en la asignación de oportunidades de los participantes. La app digitaliza y automatiza este proceso.

## Plataforma
- Aplicación móvil desarrollada en **Kotlin** (Android)
- Backend en **Python (FastAPI o Flask)** con base de datos en **MySQL** o **Firebase**
- Repositorio en GitHub para control de versiones y documentación

## Interfaz de Usuario
- Registro e inicio de sesión de clientes
- Registro de facturas con campos: local, número, fecha, monto
- Consulta de oportunidades acumuladas
- Visualización de promociones activas

## Interfaz de Administrador
- Creación y gestión de promociones (nombre, fechas, locales participantes, monto de canje por oportunidad)
- Registro de locales participantes
- Gestión de clientes y facturas registradas
- Generación de reportes para sorteo

## Funcionalidad Principal
- Registro de cliente con datos personales (nombre, cédula, correo, teléfono)
- Registro de facturas por parte del cliente
- Asignación automática de oportunidades según el monto configurado por promoción
- Consulta de oportunidades totales por cliente

## Diseño (Wireframes)
- **Pantalla de Promociones:** lista de promociones activas
- **Registro de Cliente:** formulario de datos personales
- **Ingreso de Factura:** selección de local, ingreso de datos de factura
- **Resumen de Oportunidades:** total acumulado por cliente
- **Panel Admin:** crear promociones, agregar locales, visualizar facturas

> (Los wireframes fueron elaborados mediante Figma.)
(https://www.figma.com/design/wqjZLVvB6Jd0MxEKsTTPSv/App-Promociones?node-id=0-1&t=7RznM1Ze3PZCWcHv-1)

> 
## Estado del Proyecto
Borrador inicial. En desarrollo durante el término académico.

Se crea proyecto en Android Studio para verificación de que todo este correctamente funcionando.

Se agrega el activity Login y los otros fragments al proyecto, se cargan dependencias y se verifican
(en el siguiente commit se subiran los primeros diseños de las pantallas de los fragments)

Funcionalidad de navegacion exitosa.


