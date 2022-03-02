/*
 * Created by Karic Kenan on 1.2.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package com.twoam.offers.util

import com.google.firebase.database.DatabaseException


inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    }  catch (e: Exception) {
        Resource.Failure(e)
    }
}