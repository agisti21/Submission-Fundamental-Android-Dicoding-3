package com.agisti.submissionfundamental3.helper

import android.database.Cursor
import com.agisti.submissionfundamental3.db.DatabaseContract
import com.agisti.submissionfundamental3.model.UserItems

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

//    fun mapCursorToObject(notesCursor: Cursor?): UserItems {
//        var userItems = UserItems(id = null, avatar = null, username = null, company = null, location = null)
//        notesCursor?.apply {
//            moveToFirst()
//            val id = getInt(getColumnIndexOrThrow(DatabaseContract.UserColums._ID))
//            val avatar = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.AVATAR_URL))
//            val username = getString(getColumnIndexOrThrow(DatabaseContract.UserColums.USERNAME))
//            userItems = UserItems(id = id, avatar = avatar, username = username, company = null, location = null)
//        }
//        return userItems
//    }

}