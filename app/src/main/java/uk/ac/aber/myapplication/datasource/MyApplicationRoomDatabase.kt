package uk.ac.aber.myapplication.datasource

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.myapplication.datasource.util.DayOfWeekConverter
import uk.ac.aber.myapplication.datasource.util.GenderConverter
import uk.ac.aber.myapplication.datasource.util.LocalDateTimeConverter
import uk.ac.aber.myapplication.datasource.util.LocalTimeConverter
import uk.ac.aber.myapplication.model.Student
import uk.ac.aber.myapplication.model.Gender
import uk.ac.aber.myapplication.model.StudentDao
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime


@Database(
    entities = [Student::class],
    version = 1,
)
@TypeConverters(
    LocalDateTimeConverter::class,
    GenderConverter::class,
    DayOfWeekConverter::class,
    LocalTimeConverter::class)
abstract class  MyApplicationRoomDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        private var instance: MyApplicationRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        @Synchronized
        fun getDatabase(context: Context): MyApplicationRoomDatabase? {
            if (instance == null) {
                instance =
                    Room.databaseBuilder<MyApplicationRoomDatabase>(
                        context.applicationContext,
                        MyApplicationRoomDatabase::class.java,
                        "myapplication_database"
                    )
                        .allowMainThreadQueries()
                        .addCallback(roomDatabaseCallback(context))
                        //.addMigrations(MIGRATION_1_2, MIGRATION_2_3
                        .build()
            } // if
            return instance
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    coroutineScope.launch {
                        populateDatabase(context, getDatabase(context)!!)
                    }
                }
            }
        }

        private suspend fun populateDatabase(
            context: Context,
            instance: MyApplicationRoomDatabase
        ) {
            val newStudent = LocalDateTime.now().minusDays(365)
            val oldStudent = LocalDateTime.now()


            val student1 = Student(
                0,
                "Norbert",
                "Gierczak",
                "1234567654",
                "1234567654",
                "1234567654",
                Gender.MALE,
                DayOfWeek.SUNDAY,
                LocalTime.of(10, 0),
                0,
                50,
                "Secondary",
                1,
                newStudent,
                LocalDateTime.now(),
                "a.png"
            )
            val student2 = Student(
                0,
                "Norbert",
                "Gierczak",
                "1234567654",
                "1234567654",
                "1234567654",
                Gender.MALE,
                DayOfWeek.SUNDAY,
                LocalTime.of(10, 0),
                0,
                50,
                "Secondary",
                1,
                oldStudent,
                LocalDateTime.now(),
                "a.png"
            )
            val studentList = mutableListOf(
                student1,
                student2
            )


            val dao = instance.studentDao()
            dao.insertMultipleStudents(studentList)
        }
    }
}