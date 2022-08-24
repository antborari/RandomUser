package com.randomco.testusers.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.randomco.testusers.data.database.dao.UserDao
import com.randomco.testusers.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}