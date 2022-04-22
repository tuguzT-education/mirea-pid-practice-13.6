package io.github.tuguzt.multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.multipleroomtables.entities.School
import io.github.tuguzt.multipleroomtables.entities.Student

data class SchoolWithStudents(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName",
    )
    val students: List<Student>,
)
