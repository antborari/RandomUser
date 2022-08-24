package com.randomco.testusers.domain.model

import android.os.Parcelable
import com.randomco.testusers.data.database.entities.UserEntity
import com.randomco.testusers.data.model.UserModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uuid: String,
    val name: String,
    val surname: String,
    val email: String,
    val picture: String,
    val phone: String,
    val gender: String,
    val street: String,
    val city: String,
    val state: String,
    val latitude: String,
    val longitude: String,
    val date: String,
    var isFavourite: Boolean = false,
) : Parcelable

fun UserModel.toDomain() = User(
    uuid = loginModel.uuid,
    name = nameModel.name,
    surname = nameModel.surname,
    email = email,
    picture = pictureModel.picture,
    phone = phone,
    gender = gender,
    street = location.street.run {
        number.plus(" ").plus(name)
    },
    city = location.city,
    state = location.state,
    latitude = location.coordinates.latitude,
    longitude = location.coordinates.longitude,
    date = registered.date
)

fun UserEntity.toDomain() = User(
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
    isFavourite = isFavourite
)