# SearchEngineMercadoLibre
##Hola Soy JacobClol y les presento este proyecto [LinkedIn](https://www.linkedin.com/in/jacobcl/)

##Descripción 

######Arquitectura

Estas aplicación consta de un buscador que consume las API de mercadolibre de libre acceso para buscar item, los cuales se listan y se puede visualizar su detalle.

En la construicción de esta aplicación se elegio utilizar el patron de diseño MVVM convinado con una CLEAN ARCHITECTURE con la finalidad de ser escalable y más facil de mantener para ello se creo el siguente sistema de directorios:

![architecturelayers](https://user-images.githubusercontent.com/32649079/219828537-5ffc13b9-2848-409d-a744-822521b12d75.png)

Como podemos ver se eligio tres capas la de UI, dominio y data, donde en cada modelo se crea un mapper permitiendo una mejor separación de las capas, con sus respectivos casos de usto y respositorios. De igual manera se toma la idea del patron DATASOURCE para definir dos fuentes de datos, una remota utilizando RETROFIT para las llamadas al servidor y otra local utilizando ROOM como ORM para la gestion de la base de datos, en esta última se le permite al usuario guardar localmente los items favoritos. También van encontra que para los llamados a las diferenets capas se utiliza CORRUTINAS y una inyección de dependencias con DAGGER HILT.

#######Frontend

Lo primero que considero que se debe hacer siguiendo consejos de otros desarrolladores es tenr al menos una plantilla inicial de los componentes principales que se requiren para cada vista, para ello utilice la herramienta de MIRO y este es el diseño con que se arranco:

![miroDesign](https://user-images.githubusercontent.com/32649079/219828184-5d3b4f98-7376-43cd-89df-3b7c317b1b86.png)

Para la parte de UI se utiliza una única ACTIVITY y FRAGMENTS con la finalidad de utilizar el NAVIGATION VIEW, esto mejora la navegabilidad, el envio de parámetros con SAVE ARGS que unión con DAGGER HILT y el VIEW MODEL ayuda a controlar el estado de la vista y mantener su configuración, por ejemplo, maneter los datos al momento de rotar la pantalla y evitar llamdos innecesarios a los casos de uso.

Para mostra las imagenes se utiliza la libreria de GLIDE. 

######Manejo de Errores

Para tener un control del lado del desarrollador de los errores, se integra FIREBASE CRASHLYTICS y un Interceptor para el cliente HTTP que permite visualizar los objetos relacionados con las llamas al servidor. Esto es importante para poder gestionar las fallas que se presenten al momento de su uso y ayuda a generar cambios que mejoren la aplicación. Por el lado del usuario se utiliza de momento, mensajes TOAST que le den una realimentación de ciertos eventos que se desencadenan con la interacción de la UI, por ejemplo, cuando se realiza una busqueda y no se optiene algun resultado, o cuando se ha guardado un favorito exitosamente.

######Testing

He agregado una serie de test utilizando MOCK que permiten verificar el funcionamiento de los casos de uso, repositorio y datasource, asi como verificar que están actualziando los LIVEDATA del viewmodel para actualizar las vistas.


######Versionamiento

El proyecto utiliza consta de dos ramas una main y una develop lo que me permit en una rama con los cambios estables y otra para ir emplementando los cambios que se van agregando hasta enterlos estables. 

Para finalizar recomiendo utilizar un plugin que convierte los JSON a las data class que se puede instalar en el Android Studio realmente ahorra mucho tiempo.

######Referencias para que se guíen

*[API Mercado Libre](https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas)
*[Android Docuementación viewmodel](https://www.linkedin.com/in/jacobcl/)
*[Ejemplo corrutinas cursokotlin.com](https://cursokotlin.com/tutorial-retrofit-2-en-kotlin-con-corrutinas-consumiendo-api-capitulo-20-v2/)
*[loremflickr imagenes aleatórias](https://loremflickr.com/)
