package com.lucaparlapiano.geoappdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lucaparlapiano.geoappdemo.model.poiPoint
import kotlinx.coroutines.CoroutineScope

@Database(entities = [poiPoint::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun poiPointDao(): poiPointDao
    /*
    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }*/

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase? {
            val tempInstance = INSTANCE
            if (INSTANCE != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}