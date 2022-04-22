package io.github.tuguzt.multipleroomtables

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import io.github.tuguzt.multipleroomtables.databinding.ActivityMainBinding
import io.github.tuguzt.multipleroomtables.entities.Student
import io.github.tuguzt.multipleroomtables.entities.Subject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val database by lazy {
        Room.databaseBuilder(this, SchoolDatabase::class.java, "school.db").build()
    }

    private lateinit var items: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val students = DataExample.students.map(Student::studentName)
        binding.rbStudent.setOnClickListener {
            binding.listCaption.text = getString(R.string.student_subjects)

            items = students
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            binding.spinner.adapter = adapter
        }

        val subjects = DataExample.subjects.map(Subject::subjectName)
        binding.rbSubject.setOnClickListener {
            binding.listCaption.text = getString(R.string.student_study)

            items = subjects
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            binding.spinner.adapter = adapter
        }

        val schoolDao = database.schoolDao

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val item = items[position]
                lifecycleScope.launch {
                    val results = when (item) {
                        in students -> schoolDao.getSubjectsOfStudent(item)
                            .map { it.subjects.map(Subject::subjectName) }
                            .flatten()
                        in subjects -> schoolDao.getStudentsOfSubject(item)
                            .map { it.students.map(Student::studentName) }
                            .flatten()
                        else -> emptyList()
                    }
                    binding.recyclerView.adapter = ResultAdapter(results)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

        lifecycleScope.launch {
            DataExample.directors.forEach { schoolDao.insertDirector(it) }
            DataExample.schools.forEach { schoolDao.insertSchool(it) }
            DataExample.subjects.forEach { schoolDao.insertSubject(it) }
            DataExample.students.forEach { schoolDao.insertStudent(it) }
            DataExample.studentSubjectRelations.forEach { schoolDao.insertStudentSubjectCrossRef(it) }
        }
    }
}
