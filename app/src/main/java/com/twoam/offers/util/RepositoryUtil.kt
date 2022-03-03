

package com.twoam.offers.util

import android.util.Log


inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    }  catch (e: Exception) {
        Log.d("safeCall", "safeCall: $e")
        Resource.Failure(e)
    }
}