package io.github.tuguzt.multipleroomtables

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.tuguzt.multipleroomtables.entities.Director
import io.github.tuguzt.multipleroomtables.entities.School
import io.github.tuguzt.multipleroomtables.entities.Student
import io.github.tuguzt.multipleroomtables.entities.Subject
import io.github.tuguzt.multipleroomtables.entities.relations.StudentSubjectCrossRef

@Database(
    entities = [
        School::class,
        Student::class,
        Director::class,
        Subject::class,
        StudentSubjectCrossRef::class,
    ],
    version = 1,
)
abstract class SchoolDatabase : RoomDatabase() {
    abstract val schoolDao: SchoolDao
}
