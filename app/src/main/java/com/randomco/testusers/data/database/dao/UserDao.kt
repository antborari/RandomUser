package com.randomco.testusers.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.randomco.testusers.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY name")
    fun getAllUser(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: List<UserEntity>)

    @Query("DELETE FROM user_table WHERE uuid = :uuid")
    suspend fun deleteUser(uuid: String)

    @Query("UPDATE user_table SET isFavourite = :isFavourite WHERE uuid = :uuid")
    suspend fun updateUser(uuid: String, isFavourite: Boolean)
}