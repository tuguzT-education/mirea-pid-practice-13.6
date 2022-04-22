package io.github.tuguzt.multipleroomtables.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import io.github.tuguzt.multipleroomtables.entities.Director
import io.github.tuguzt.multipleroomtables.entities.School

data class SchoolAndDirector(
    @Embedded val school: School,
    @Relation(
        parentColumn = "schoolName",
        entityColumn = "schoolName",
    )
    val director: Director,
)
