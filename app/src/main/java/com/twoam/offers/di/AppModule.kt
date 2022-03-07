package com.twoam.offers.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.twoam.offers.util.PREF_NAME
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREF_NAME)

}