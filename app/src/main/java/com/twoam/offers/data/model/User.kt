package com.twoam.offers.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id:String="1",
    var name: String = "",
    var email: String = "",
    var telephone: String = "",
    var rule: String = "",
    var password: String = "",
    var type: String = "employee",
) : Parcelable