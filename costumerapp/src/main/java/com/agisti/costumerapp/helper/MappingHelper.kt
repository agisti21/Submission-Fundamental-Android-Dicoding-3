package com.agisti.costumerapp.helper

import android.database.Cursor
import com.agisti.costumerapp.db.DatabaseContract
import com.agisti.costumerapp.model.UserItems

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserItems>{
        val noteList = ArrayList<UserItems>()
        notesCursor?.apply {
            while (moveToNext()){
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColums._ID))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.AVATAR_URL))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.USERNAME))
                noteList.add(UserItems(id = id, avatar = avatar, username = username, company = null, location = null))
            }
        }
        return noteList
    }
}