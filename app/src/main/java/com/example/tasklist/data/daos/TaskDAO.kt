package com.example.tasklist.data.daos

import android.content.ContentValues
import android.content.Context
//import android.provider.BaseColumns
import com.example.tasklist.data.entities.Task
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

            db.close()
        }

        fun update(task: Task) {
            val db = databaseManager.writableDatabase    //  db: Obtiene una base de datos en modo escritura.

            val values = ContentValues()    // Valores de contenido (CONTENTVALUES): Almacena los nuevos valores de las columnas NAME y DONE.
            values.put(Task.COLUMN_NAME_TITLE, task.name)
            values.put(Task.COLUMN_NAME_DONE, task.done)

            val updatedRows = db.update(    // db.update: Actualiza la fila correspondiente en la tabla Task basada en el ID de la tarea.
                Task.TABLE_NAME,
                values,
                "${DatabaseManager.COLUMN_NAME_ID} = ${task.id}",
                null
            )
        }

        fun delete(task: Task) {
            val db = databaseManager.writableDatabase   // "db" Obtiene una BASE DE DATOS en modo escritura.

            val deletedRows = db.delete(Task.TABLE_NAME, "${DatabaseManager.COLUMN_NAME_ID} = ${task.id}", null)

            //  "db.delete": Elimina la fila de la tabla Task que coincide con el ID de la tarea.

        }

            fun find(id: Int) : Task? {
                val db = databaseManager.readableDatabase

                val projection = Task.COLUMN_NAMES

                val cursor = db.query(
                    Task.TABLE_NAME,                        // The table to query
                    projection,                             // The array of columns to return (pass null to get all)
                    "${DatabaseManager.COLUMN_NAME_ID} = $id",      // The columns for the WHERE clause
                    null,                         // The values for the WHERE clause
                    null,                            // don't group the rows
                    null,                             // don't filter by row groups
                    null                             // The sort order
                )

                var task: Task? = null
                if (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_NAME_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                    val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) == 1
                    task = Task(id, name, done)
                }
                cursor.close()
                db.close()
                return task
            }

            fun findAll() : List<Task> {
                val db = databaseManager.readableDatabase

                val projection = Task.COLUMN_NAMES

                val cursor = db.query(
                    Task.TABLE_NAME,                        // The table to query
                    projection,                             // The array of columns to return (pass null to get all)
                    null,                            // The columns for the WHERE clause
                    null,                         // The values for the WHERE clause
                    null,                            // don't group the rows
                    null,                             // don't filter by row groups
                    "${Task.COLUMN_NAME_DONE} ASC"                             // The sort order
                )

                var tasks = mutableListOf<Task>()
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseManager.COLUMN_NAME_ID))
                    val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                    val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) == 1
                    val task = Task(id, name, done)
                    tasks.add(task)
                }
                cursor.close()
                db.close()
                return tasks
            }
        }

/*  RESUMEN:
La clase TaskDAO proporciona métodos para manejar operaciones CRUD (Create, Read, Update, Delete)
en una tablaTaskde una base de datos SQLite. Utiliza el DatabaseManager para obtener instancias de la
base de datos y ContentValuespara mapear los valores de las columnas de la tabla. Las consultas y
manipulaciones se realizan utilizando los métodos insert,update, delete,findy findAll.
 */