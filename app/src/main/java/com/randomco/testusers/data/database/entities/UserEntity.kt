package com.randomco.testusers.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.randomco.testusers.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "uuid") val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "picture") val picture: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "isFavourite") val isFavourite: Boolean = false
)

fun User.toDatabase() = UserEntity(
    uuid = uuid,
    name = name,
    surname = surname,
    email = email,
    picture = picture,
    phone = phone,
    gender = gender,
    street = street,
    city = city,
    state = state,
    latitude = latitude,
    longitude = longitude,
    date = date,
)