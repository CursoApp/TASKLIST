package com.example.tasklist.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasklist.data.entities.Task
import com.example.tasklist.data.daos.TaskDAO
import com.example.tasklist.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

    private lateinit var taskDAO: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDAO = TaskDAO(this)

        binding.saveButton.setOnClickListener {
            val taskName = binding.nameEditText.text.toString()
            val task = Task(-1, taskName)
            taskDAO.insert(task)
            Toast.makeText(this, "Tarea guardada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
        /*
        Este segundo comando es para "secondButton" para que borre lo escrito en búsqueda:
         binding PUNTO nombre del boton + buscar click para que encontrar "clickstener", Abrir llave
         Volver a escribir el binding PUNTO el nombre del ID de "edit text" PUNTO + set text
        abrir paréntesis y PONER COMILLAS VACÏAS, para que se entienda que se debe poner la busqueda en VACÍO

        */
