package com.gianghv.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gianghv.android.database.dao.EvaluationDao
import com.gianghv.android.database.dao.ImageDao
import com.gianghv.android.database.dao.RoomDao
import com.gianghv.android.database.dao.UserDao
import com.gianghv.android.database.table.DatabaseEvaluationImage
import com.gianghv.android.database.table.DatabaseRoom
import com.gianghv.android.database.table.DatabaseRoomEvaluation
import com.gianghv.android.database.table.DatabaseRoomImage
import com.gianghv.android.database.table.DatabaseUser

@Database(
    entities = [
        DatabaseRoom::class, DatabaseEvaluationImage::class,
        DatabaseRoomEvaluation::class,
        DatabaseRoomImage::class,
        DatabaseUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDatabase : RoomDatabase() {
    abstract val roomsDao: RoomDao
    abstract val evaluationsDao: EvaluationDao
    abstract val imagesDao: ImageDao
    abstract val usersDao: UserDao
}
