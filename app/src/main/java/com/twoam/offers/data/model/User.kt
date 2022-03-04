package com.twoam.offers.data.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = "1",
    var name: String = "",
    var email: String = "",
    var telephone: String = "",
    var rule: String = "",
    var password: String = "",
    var type: String = "employee",
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "telephone" to telephone,
            "rule" to rule,
            "type" to type
        )
    }

}
