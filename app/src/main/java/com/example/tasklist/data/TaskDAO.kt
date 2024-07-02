package com.example.tasklist.data

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.tasklist.utils.DatabaseManager

    /*DEFINICIÓN DE DAO:
    La clase TaskDAO(Data Access Object) se utiliza para manejar las operaciones de la base de datos
    relacionados con la entidad Task. Estas operaciones incluyen insertar, actualizar, eliminar y
    consultar tareas en una base de datos SQLite.
    */

        //El contexto (CONTEXT) de la aplicación, es NECESARIO para inicializar el DatabaseManager.
class TaskDAO(context: Context) {

    private val databaseManager: DatabaseManager = DatabaseManager(context)
            // Administrador de bases de datos: Una instancia de "DatabaseManager", que se
            // encarga de la creación, actualización y gestión de la base de datos.

    fun insert(task: Task) {
        val db = databaseManager.writableDatabase  // Val "DB" Obtiene una base de datos en modo escritura.

        val values = ContentValues()   //Una clase que permite almacenar pares clave-valor, que representan las columnas y sus valores.
        values.put(Task.COLUMN_NAME_TITLE, task.name)   //valores.poner (VALUES.PUT): Inserta los valores de las columnas NAME y DONE de la tarea en el ContentValues.
        values.put(Task.COLUMN_NAME_DONE, task.done)

        val newRowId = db.insert(Task.TABLE_NAME, null, values)   // db.insert: Inserta una nueva fila en la tabla Task y devuelve el ID de la nueva fila.
        task.id = newRowId.toInt()   //  Asigna el ID generado a la tarea.
    }

    fun update(task: Task) {
        val db = databaseManager.writableDatabase    //  db: Obtiene una base de datos en modo escritura.

        val values = ContentValues()    // Valores de contenido (CONTENTVALUES): Almacena los nuevos valores de las columnas NAME y DONE.
        values.put(Task.COLUMN_NAME_TITLE, task.name)
        values.put(Task.COLUMN_NAME_DONE, task.done)

        val updatedRows = db.update(    // db.update: Actualiza la fila correspondiente en la tabla Task basada en el ID de la tarea.
            Task.TABLE_NAME,
            values,
            "${BaseColumns._ID} = ${task.id}",
            null
        )
    }

    fun delete(task: Task) {
        val db = databaseManager.writableDatabase   // "db" Obtiene una BASE DE DATOS en modo escritura.

        val deletedRows = db.delete(Task.TABLE_NAME, "${BaseColumns._ID} = ${task.id}", null)

        //  "db.delete": Elimina la fila de la tabla Task que coincide con el ID de la tarea.

    }

    fun find(id: Int) : Task? {
        val db = databaseManager.readableDatabase   //  "db": Obtiene una base de datos en modo lectura.

        val projection = arrayOf(BaseColumns._ID, Task.COLUMN_NAME_TITLE, Task.COLUMN_NAME_DONE)   // "proyección": Define las columnas que se quieren recuperar.

        val cursor = db.query(       //  "db.query": Realiza una consulta en la tabla Task para encontrar la fila con el ID especificado.
            Task.TABLE_NAME,               // La tabla a consultar / The table to query
            projection,                    // La matriz de columnas a devolver (pase nulo para obtener "to do") / The array of columns to return (pass null to get all)
            "${BaseColumns._ID} = $id",    // Las columnas de la cláusula WHERE  /  The columns for the WHERE clause
            null,                          // Los valores de la cláusula WHERE  /  The values for the WHERE clause
            null,                          // Para q no agrupes las filas  /  don't group the rows
            null,                          // no filtrar por grupos de filas  /  don't filter by row groups
            null                           // el orden de clasificación  /  The sort order
        )

        var task: Task? = null         //  "cursor": Un cursor es el que permite recorrer los resultados de la consulta.
        if (cursor.moveToNext()) {       //  "cursor.moveToNext": Mueve el cursor al siguiente resultado (en este caso, solo hay un resultado).
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))   // "cursor.getInt y cursor.getString": Recupera los valores de las columnas especificadas.
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) == 1
            task = Task(id, name, done)
        }
        cursor.close()    // Cierra el cursor
        db.close()         // Cierra la Base de Datos
        return task
    }


    fun findAll() : List<Task> {
        val db = databaseManager.readableDatabase   //"db": Obtiene una base de datos en modo lectura.

        val projection = arrayOf(BaseColumns._ID, Task.COLUMN_NAME_TITLE, Task.COLUMN_NAME_DONE)   //"proyection": Define las columnas que se quieren recuperar.

        val cursor = db.query(     // "b.query": Realiza una consulta en la tablaTaskpara recuperar todas las filas.  "cursor": Un cursor que permite recorrer los resultados de la consulta.
            Task.TABLE_NAME,        // La tabla a consultar / The table to query
            projection,             // La matriz de columnas a devolver (pase nulo para obtener "to do") / The array of columns to return (pass null to get all)
            null,                   // Las columnas de la cláusula WHERE  /  The columns for the WHERE clause
            null,                   // Los valores de la cláusula WHERE  /  The values for the WHERE clause
            null,                   // Para q no agrupes las filas  /  don't group the rows
            null,                   // no filtrar por grupos de filas  /  don't filter by row groups
            null                    // el orden de clasificación  /  The sort order
        )

        var tasks = mutableListOf<Task>()
        while (cursor.moveToNext()) {    //cursor.moveToNext: Itera sobre todos los resultados.
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))   //cursor.getInt y cursor.getString: Recupera los valores de las columnas especificadas.
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) == 1
            val task = Task(id, name, done)
            tasks.add(task)       // tasks.add(task): Añade cada tarea recuperada a la lista de tareas.
        }
        cursor.close()    // cursor.cerrar: Cierra el cursor.

        db.close()   //  db.close: Cierra la base de datos.

        return tasks   //  devolver tareas: Devuelve la lista de todas las tareas.
    }
}

/*  RESUMEN:
La clase TaskDAO proporciona métodos para manejar operaciones CRUD (Create, Read, Update, Delete)
en una tablaTaskde una base de datos SQLite. Utiliza el DatabaseManager para obtener instancias de la
base de datos y ContentValuespara mapear los valores de las columnas de la tabla. Las consultas y
manipulaciones se realizan utilizando los métodos insert,update, delete,findy findAll.
 */