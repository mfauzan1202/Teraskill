package id.co.mka.teraskill.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.co.mka.teraskill.data.responses.AllClassResponse
import id.co.mka.teraskill.utils.LearningPathConverter
import id.co.mka.teraskill.utils.MentorInfoConverter

@Database(
    entities = [AllClassResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MentorInfoConverter::class, LearningPathConverter::class)
abstract class ClassDatabase : RoomDatabase(){
    companion object{
        @Volatile
        private var INSTANCE: ClassDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ClassDatabase{
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ClassDatabase::class.java, "class_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}