# Torres de Hanoi - Juego de Colores

Proyecto en Java basado en las Torres de Hanoi, adaptado a un juego de bloques de colores. El objetivo es reorganizar las varillas hasta que los bloques queden agrupados correctamente por color.

El proyecto incluye una version por consola y una interfaz grafica desarrollada con Swing para visualizar el estado de las varillas y realizar movimientos mediante botones.

## Caracteristicas

- Juego desarrollado en Java.
- Interfaz grafica con Swing.
- Visualizacion de las tres varillas y sus bloques.
- Movimiento de bloques entre varillas mediante botones.
- Posibilidad de mover varios bloques a la vez si son consecutivos y del mismo color.
- Validacion de movimientos no permitidos.
- Opcion para cargar una partida guardada.
- Guardado de movimientos en un archivo.
- Funcion de deshacer el ultimo movimiento.
- Deteccion de partida completada.

## Funcionamiento del juego

El juego contiene tres varillas con bloques de diferentes colores. Cada bloque se representa con una letra:

- `R`: rojo
- `V`: verde
- `A`: azul
- `M`: morado
- `N`: naranja
- `Y`: amarillo

El jugador debe mover bloques entre las varillas hasta conseguir que cada varilla contenga bloques del mismo color.

Solo se pueden mover varios bloques a la vez cuando esos bloques estan juntos en la parte superior de una varilla y son del mismo color.

## Interfaz grafica

La interfaz permite realizar movimientos sin usar la consola. Cada boton representa un movimiento entre dos varillas:

- `A -> B`
- `A -> C`
- `B -> A`
- `B -> C`
- `C -> A`
- `C -> B`

Al pulsar un boton, el programa pregunta cuantos bloques se quieren mover. Si el movimiento no es valido, se muestra un mensaje de error.

## Estructura principal del proyecto

```text
src/
└── torres_de_hanoi/
    ├── Torres_de_hanoi.java
    ├── JuegoColores.java
    ├── JuegoManager.java
    ├── VarillasVisualizer.java
    ├── GestorArchivos.java
    └── ConsoleCapturer.java
```

## Clases principales

### `Torres_de_hanoi`

Clase principal del proyecto. Inicia el programa, pregunta si se desea cargar una partida y abre la interfaz grafica.

### `JuegoColores`

Contiene la logica principal del juego:

- Estado de las varillas.
- Movimiento de bloques.
- Validacion de movimientos.
- Historial para deshacer.
- Comprobacion de victoria.

### `VarillasVisualizer`

Gestiona la interfaz grafica del juego. Dibuja las varillas y los bloques, ademas de incluir los botones para realizar movimientos.

### `JuegoManager`

Gestiona la version por consola del juego. Permite introducir comandos manualmente, seleccionar varilla origen, varilla destino y cantidad de bloques.

### `GestorArchivos`

Se encarga de guardar y cargar partidas desde archivos.

### `ConsoleCapturer`

Captura la salida de consola para actualizar la visualizacion grafica del estado de las varillas.

## Requisitos

- Java JDK 8 o superior.
- Un IDE compatible con Java, por ejemplo:
  - NetBeans
  - IntelliJ IDEA
  - Eclipse
  - Visual Studio Code

## Como ejecutar el proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/usuario/nombre-del-repositorio.git
```

2. Abre el proyecto en tu IDE.

3. Ejecuta la clase principal:

```text
Torres_de_hanoi.java
```

4. El programa preguntara si deseas cargar una partida guardada.

5. Se abrira la interfaz grafica para jugar usando los botones.

## Ejemplo de movimiento

Si quieres mover bloques de la varilla A a la varilla B:

1. Pulsa el boton `A -> B`.
2. Introduce cuantos bloques quieres mover.
3. El programa comprobara si el movimiento es valido.
4. Si es correcto, la interfaz se actualizara.

## Reglas de movimiento

- No se puede mover desde una varilla vacia.
- No se puede mover a una varilla llena.
- No se puede mover una cantidad menor o igual a cero.
- Solo se pueden mover varios bloques si son consecutivos y tienen el mismo color.
- Si el movimiento no cumple las reglas, se muestra un aviso.

## Estado del proyecto

Proyecto en desarrollo y mejora. Actualmente cuenta con logica de juego, guardado/carga de partidas, modo consola e interfaz grafica con botones.

## Autor

Proyecto realizado por Rafael.
