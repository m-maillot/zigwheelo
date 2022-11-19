package fr.racomach.zigwheelo.storage

import android.content.SharedPreferences
import fr.racomach.zigwheelo.model.Cyclist
import fr.racomach.zigwheelo.model.CyclistId
import javax.inject.Inject

interface CyclistStorage {
    fun saveCyclist(username: String, id: CyclistId): Cyclist
}

class CyclistSharedPrefs @Inject constructor(
    private val
    sharedPrefs: SharedPreferences
) : CyclistStorage {

    override fun saveCyclist(username: String, id: CyclistId): Cyclist {
        sharedPrefs.edit().apply {
            putString("USER_NAME", username)
            putString("USER_ID", id.toString())
        }.apply()
        return Cyclist(id, username)
    }
}