package com.randomco.testusers.data.model

import com.google.gson.annotations.SerializedName

data class ResultModel(
    @SerializedName("results")
    val results: List<UserModel>
)

data class UserModel(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("location")
    val location: LocationModel,
    @SerializedName("login")
    val loginModel: LoginModel,
    @SerializedName("name")
    val nameModel: NameModel,
    @SerializedName("email")
    val email: String,
    @SerializedName("picture")
    val pictureModel: PictureModel,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("registered")
    val registered: RegisteredModel,
)

data class LocationModel(
    @SerializedName("street")
    val street: StreetModel,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("coordinates")
    val coordinates: CoordinatesModel,
)

data class CoordinatesModel(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
)

data class StreetModel(
    @SerializedName("number")
    val number: String,
    @SerializedName("name")
    val name: String,
)

data class LoginModel(
    @SerializedName("uuid")
    val uuid: String
)

data class NameModel(
    @SerializedName("first")
    val name: String,
    @SerializedName("last")
    val surname: String,
)

data class PictureModel(
    @SerializedName("large")
    val picture: String,
)

data class RegisteredModel(
    @SerializedName("date")
    val date: String,
)