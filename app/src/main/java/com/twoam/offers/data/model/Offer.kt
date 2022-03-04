package com.twoam.offers.data.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    var id: String,
    var description: String,
    var salary: String,
    var equite: String,
    var bonus: String,
    var culture: String,
    var learning: String,
    var role: String,
    var team_details: String,
    var organization_details: String,
    var offer_status: String
) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "description" to description,
            "salary" to salary,
            "equite" to equite,
            "bonus" to bonus,
            "culture" to culture,
            "learning" to learning,
            "role" to role,
            "team_details" to team_details,
            "organization_details" to organization_details,
            "offer_status" to offer_status
        )
    }
}