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
                DayOfWeek.WEDNESDAY,
                LocalTime.of(10, 0),
                0,
                50,
                "Secondary",
                1,
                newStudent,
                LocalDateTime.now(),
                "file:///android_asset/images/a.jpg"
            )
            val student2 = Student(
                0,
                "Clara",
                "Logan",
                "1234567654",
                "1234567654",
                "1234567654",
                Gender.FEMALE,
                DayOfWeek.MONDAY,
                LocalTime.of(7, 0),
                0,
                50,
                "Primary",
                3,
                oldStudent,
                LocalDateTime.now(),
                "file:///android_asset/images/b.jpg"
            )
            val student3 = Student(
                0,
                "Paul",
                "Smith",
                "741852963",
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
                "file:///android_asset/images/c.jpg"
            )

            val student4 = Student(
                0,
                "Robert",
                "Lewandowski",
                "789456125",
                "1234567654",
                "1234567654",
                Gender.MALE,
                DayOfWeek.TUESDAY,
                LocalTime.of(8, 0),
                0,
                60,
                "Secondary",
                3,
                oldStudent,
                LocalDateTime.now(),
                "file:///android_asset/images/d.jpg"
            )
            val student5 = Student(
                0,
                "Jenny",
                "Moore",
                "789456125",
                "1234567654",
                "1234567654",
                Gender.FEMALE,
                DayOfWeek.THURSDAY,
                LocalTime.of(9, 0),
                0,
                60,
                "Secondary",
                1,
                oldStudent,
                LocalDateTime.now(),
                "file:///android_asset/images/e.jpg"
            )
            val student6 = Student(
                0,
                "Matt",
                "Starling",
                "963852741",
                "1234567654",
                "1234567654",
                Gender.MALE,
                DayOfWeek.FRIDAY,
                LocalTime.of(12, 0),
                0,
                70,
                "Secondary",
                2,
                oldStudent,
                LocalDateTime.now(),
                "file:///android_asset/images/h.jpg"
            )
            val studentList = mutableListOf(
                student1,
                student2,
                student3,
                student4,
                student5,
                student6
            )


            val dao = instance.studentDao()
            dao.insertMultipleStudents(studentList)
        }
    }
}