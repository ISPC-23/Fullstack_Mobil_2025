# 🚴‍♀️ Proyecto Integrador - Tienda Full (Versión Android)

Proyecto grupal para el módulo "Programador de Aplicaciones Móviles" de la tecnicatura en desarrollo web y aplicaciones digitales del ISPC . Está compuesto por tres espacios curriculares: Aplicaciones para móviles, Ciberseguridad, Testeador de Software y Proyecto Integrador II.

## 👩‍👩‍👦‍👦 Integrantes

- Barletta Fernando | [GitHub](https://github.com/Ferbarletta)
- Beltramone Mateo | [GitHub](https://github.com/Mateo88XD)
- Blasiche Andrés | [GitHub](https://github.com/blasichea)
- Cabrera Verónica | [GitHub](https://github.com/Verosolc30)
- Castillo Fernanda | [GitHub](https://github.com/FernandaACastillo)
- Gillini Emiliano | [GitHub](https://github.com/emigillini)
- Krenn Federico | [GitHub](https://github.com/fedekrenn)
- Liendo Germán Emanuel | [GitHub](https://github.com/g3rm6n)


## 💻 Descripción del proyecto

Este proyecto es una aplicación móvil Android para la tienda ecommerce de bicicletas "Tienda Full". El frontend está desarrollado en Java para Android y consume una API REST implementada previamente en Django. El proyecto busca ofrecer una experiencia completa de compra de bicicletas, incluyendo un catálogo de productos, detalles de cada uno, y la posibilidad de realizar pedidos.

El backend de la aplicación está desplegado utilizando Django y se conecta a una base de datos MySQL alojada en la nube.

Este proyecto es la evolución del desarrollo previo realizado en Angular y Django, manteniendo la API y backend ya implementados.

![image](https://github.com/ISPC-23/FullStack2023/assets/132081100/29c5e580-8044-4560-8a41-f70178a0cc54)

## 🛠 Instalación y ejecución

### Requisitos

- Android Studio instalado
- Java Development Kit (JDK) 17 o superior
- Conexión a la API de Django (detalles en el archivo de configuración)

### Pasos

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/ISPC-23/Fullstack_Mobil_2024.git
   ```
2. Importar el proyecto en Android Studio.
3. Configurar la conexión a la API de Django en el archivo `config.properties`.
4. Ejecutar la aplicación en un emulador o dispositivo físico.

### Backend 

El backend sigue siendo el mismo desarrollado en Django. Para más detalles sobre su configuración y ejecución, consultar la [Documentación backend](https://github.com/ISPC-23/FullStack2024).

### 📃 Documentación:

Toda la documentación del proyecto tal como el documento IEEE830, los diagramas (de clase, entidad-relación, etc), la documentación de las ceremonias, etc. Pueden encontrarse en la  [Wiki del repositorio](https://github.com/ISPC-23/Fullstack_Mobil_2024/wiki)

## ❗ Puntos a tener en cuenta

- Para hacer el programa más óptimo y ejecutable en cualquier entorno, se optó por subir la base de datos a un servidor en la nube, el mismo es [Clevercloud](https://www.clever-cloud.com/), los datos de conexión están en el archivo de configuración y modificando sólo los datos por el localhost puede ejecutarse con una DB local. De igual manera, en la carpeta database está el archivo "db_script.sql" con el script que crea la db para poder ejecutarla en local.

