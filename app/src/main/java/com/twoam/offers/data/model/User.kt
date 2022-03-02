package com.twoam.offers.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id:String="",
    var name: String = "",
    var email: String = "",
    var telephone: String = "",
    var rule: String = "",
    var password: String = ""
) : Parcelable