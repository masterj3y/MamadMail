package masterj3y.github.mamadmail.common.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCE_NAME = "app_preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

suspend fun DataStore<Preferences>.writeString(key: String, value: String) =
    edit { pref -> pref[stringPreferencesKey(key)] = value }

fun DataStore<Preferences>.readString(key: String): Flow<String?> =
    data.map { pref -> pref[stringPreferencesKey(key)] }

suspend fun DataStore<Preferences>.writeInt(key: String, value: Int) =
    edit { pref -> pref[intPreferencesKey(key)] = value }

fun DataStore<Preferences>.readInt(key: String): Flow<Int?> =
    data.map { pref -> pref[intPreferencesKey(key)] }

suspend fun DataStore<Preferences>.writeDouble(key: String, value: Double) =
    edit { pref -> pref[doublePreferencesKey(key)] = value }

fun DataStore<Preferences>.readDouble(key: String): Flow<Double?> =
    data.map { pref -> pref[doublePreferencesKey(key)] }

suspend fun DataStore<Preferences>.writeLong(key: String, value: Long) =
    edit { pref -> pref[longPreferencesKey(key)] = value }

fun DataStore<Preferences>.readLong(key: String): Flow<Long?> =
    data.map { pref -> pref[longPreferencesKey(key)] }

suspend fun DataStore<Preferences>.writeBool(key: String, value: Boolean) =
    edit { pref -> pref[booleanPreferencesKey(key)] = value }

fun DataStore<Preferences>.readBool(key: String): Flow<Boolean?> =
    data.map { pref -> pref[booleanPreferencesKey(key)] }