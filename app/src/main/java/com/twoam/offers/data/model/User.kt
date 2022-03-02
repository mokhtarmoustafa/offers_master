package com.twoam.offers.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id:String,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var userName: String = ""
) : Parcelable