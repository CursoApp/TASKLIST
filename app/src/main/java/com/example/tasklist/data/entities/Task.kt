package com.example.tasklist.data.entities

import android.provider.BaseColumns
import com.example.tasklist.utils.DatabaseManager

data class Task(var id: Int, var name: String, var done: Boolean = false) {

    /*Los miembros definidos dentro de "COMPANION OBJET" pueden ser
    accedidos directamente usando el nombre de la clase sin necesidad
    de crear una instancia de la misma. */
    companion object {
        const val TABLE_NAME = "Tasks"        // Define el nombre de la tabla en la base de datos, en este caso, "Tasks".

        const val COLUMN_NAME_TITLE = "name" // Define el nombre de una columna en la tabla, en este caso, "name".

        const val COLUMN_NAME_DONE = "done"  //  Define el nombre de OTRA COLUMNA en la tabla, en este caso, "done".

        const val COLUMN_NAME_CATEGORY = "category_id"
        val COLUMN_NAMES = arrayOf(
            DatabaseManager.COLUMN_NAME_ID,
            COLUMN_NAME_TITLE,
            COLUMN_NAME_CATEGORY,
            COLUMN_NAME_DONE
        )
        /* OJO:
        Lo de abajo ES una constante que contiene una cadena de texto que representa una
        instrucción SQL para crear la tabla "Tasks". La tabla tendrá las siguientes columnas:

         - ID: Un identificador único para cada fila, de tipo INTEGER y que se incrementa
           automáticamente (PRIMARY KEY AUTOINCREMENT).
         - name: Una columna de texto (TEXT) para almacenar el nombre de la tarea.
         - done: Una columna de tipo INTEGER para indicar si la tarea está completada
           (generalmente se usa 0 para falso y 1 para verdadero).
         */
        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${DatabaseManager.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME_TITLE TEXT," +
                    "$COLUMN_NAME_CATEGORY INTEGER," +
                    "$COLUMN_NAME_DONE BOOLEAN, " +
                    "CONSTRAINT fk_category " +
                    "FOREIGN KEY($COLUMN_NAME_CATEGORY) " +
                    "REFERENCES ${Category.TABLE_NAME}(${DatabaseManager.COLUMN_NAME_ID}) ON DELETE CASCADE)"

        /*Es una constante que contiene una cadena de texto que representa
        una instrucción SQL para eliminar la tabla "Tasks" si esta existe.
        La instrucción es DROP TABLE IF EXISTS Tasks.
         */
        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}