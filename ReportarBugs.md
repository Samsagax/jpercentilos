

# Introducción #

## ¿Qué es un bug? ##

Hay un bug cuando un programa hace algo que no se esperaba que pase, usualmente causando problemas al usuario. Algunos ejemplos de Bugs son:

  * Cierre anormal o cuelgue abrupto de un programa
  * La falta (simple) de una opción en la aplicación
  * Errores inesperados, usualmente impiden que se siga usando normalmente la aplicación
  * Otros comportamientos inesperados

## Lo que no es un bug ##

  * Un pedido de soporte (por favor usar la [lista de mail](http://groups.google.com/group/jpercentilos))
  * La falta de una aplicación (requiere mucho trabajo el implementarlo)
  * Configuración incorrecta del hard

## ¿Cómo sé si es un bug? ##

Para los recién llegados, en general antes de reportar el bug es aconsejable que consulten en la lista de mail. Muchas veces se está ante problemas que no son realmente bugs, y muchos problemas comunes que son bugs ya han sido reportados (Se puede revisar la lista de issues y buscarlo)

Los usuarios con mas experiencia pueden ofrecerte ayuda para poder opinar si tu problema es un bug y si el mismo debe ser reportado.


## Antes de reportar un bug ##

  1. Estar seguro de tener la cuenta en Google – No se pueden reportar bugs sin tener una cuenta en Google. Si no tienes una cuenta en Google la puedes abrir en https://www.google.com/accounts/, hacer click en "Crear cuenta"y seguir las instrucciones.
  1. Buscar en los bugs de la página que no haya sido reportado, si hay uno abierto por favor agrega tu problema en el mismo y si te es posible cambia el status de “New” (nuevo) a “Confirmed” (confirmado).
**Importante:** Esto sólo debes hacerlo si estas un 100% seguro que es exactamente el mismo problema. Si el bug se refiere a una pieza de hardware que tienes, debes tener la misma parte o al menos una muy parecida, en caso que ésta no sea tu situación por favor abre un nuevo reporte y menciona el que ya hayas encontrado.

# ¿Cómo reportar errores? #

## Dar la información correcta! ##

Es importante que cuando se reporten se lo haga de manera correcta con la mayor cantidad de información posible. Podemos asegurar que mejorara significativamente las posibilidades de que el bug sea revisado y reparado en el corto plazo.

## Pasos para reportar un bug de forma correcta ##

  1. Busca en la página http://code.google.com/p/jpercentilos/issues/list una descripción breve de tu bug.
  1. Si piensas que tu problema no está lsitado, entra a http://code.google.com/p/jpercentilos/issues/list y haz click en "New issue".
  1. Aparecerá un formulario. Aquí puedes describir tu bug con mas detalle, incluir los pasos para reproducir el bug y que has hecho para intentar solucionarlo o probar alternativas. Incluye también la salida esperada del programa y la salida que obtuviste (si corresponde)
  1. Agrega cualquier otro dato de interés, como sistema operativo, localización, versión de la Máquina Virtual de Java (JVM) etc.
  1. Si necesitas adjuntar algun/os archivo/s (pueden ser screenshots) ve a la opción de "Attach a file". Se puede subir varios archivos en cada comentario pero no más de 10MB por comentario, por lo que si tiene mas archivos debes añadirlos en tus comentarios después de haber reportado el bug.
  1. Cuando creas que has agregado información suficiente haz click en "Submit issue".

## Despues de reportar el bug ##

Puedes agregar comentarios extra adjuntar mas archivos, para esto haz click en "Add comment an make changes". Al final del reporte del bug recibiras e-mails relativos a los cambios y respuestas al bug que hayas reportado. El seguimiento del bug no puede ser completado hasta que hayas suministrado toda la información requerida. Agrega el reporte a tus favoritos (agrega una estrella) e intenta hacer un seguimiento del mismo.

Los desarrolladores pueden preguntar por más informacián en caso de considerarlo necesario. También puede ser que te soliciten que revises éste mismo error en la versión en desarrollo del programa, en ese caso se te enviará el archivo como adjunto en un comentario del bug o vía e-mail a tu casilla de Google.

# Ciclo habitual del reporte de un bug #

El usuario experimenta un problema y reporta el bug. Si tiene suerte alguien mas confirmara su problema (No confirmes tu propio problema!!). Un developer mira el problema. Si es necesaria mas información el Developer la solicitará, la persona que reporto el bug debe responder. Este proceso se repite las veces que sea necesario.

Cuando el Developer esta satisfecho con la información recolectada el bug sera marcado como "Accepted", el Developer indicará la importancia del bug y posiblemente asignarlo, arregla el bug o lo rechaza (ya sea por que no es un bug o por que no la pena arreglarlo).

El reporte se cierra con el status de "Fixed" (Arreglo propuesto para actualización), "Won't fix" (no sera arreglado), "Invalid" (Inválido) o "Done" (Si es una tarea que no requeire código nuevo).