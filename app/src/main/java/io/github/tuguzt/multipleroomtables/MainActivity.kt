package io.github.tuguzt.multipleroomtables

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import io.github.tuguzt.multipleroomtables.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val database by lazy {
        Room.databaseBuilder(this, SchoolDatabase::class.java, "school.db").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rbStudent.setOnClickListener{
            binding.listCaption.text = getString(R.string.student_subjects)
            // так же должен меняться выпадающий список
        }

        binding.rbSubject.setOnClickListener{
            binding.listCaption.text = getString(R.string.student_study)
            // также должен меняться выпадающий список
        }

        val items = arrayOf("Здесь", "должен", "располагаться", "список", "учеников", "или предметов")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Toast.makeText(applicationContext, "При выборе должен меняться список на предметы выбранного ученика или учеников, изучающих выбранный предмет", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }

        val schoolDao = database.schoolDao
        lifecycleScope.launch {
            DataExample.directors.forEach { schoolDao.insertDirector(it) }
            DataExample.schools.forEach { schoolDao.insertSchool(it) }
            DataExample.subjects.forEach { schoolDao.insertSubject(it) }
            DataExample.students.forEach { schoolDao.insertStudent(it) }
            DataExample.studentSubjectRelations.forEach { schoolDao.insertStudentSubjectCrossRef(it) }
        }
    }
}
