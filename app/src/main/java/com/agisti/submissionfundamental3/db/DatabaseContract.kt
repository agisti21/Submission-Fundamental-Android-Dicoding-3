package com.agisti.submissionfundamental3.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHOR_USER = "com.agisti.submissionfundamental3"
    const val SCHEME_USER = "content"

    internal class UserColums : BaseColumns {
        companion object{
            const val TABLE_NAME = "user_favorite"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar"

            val CONTENT_URI_USER : Uri = Uri.Builder().scheme(SCHEME_USER)
                .authority(AUTHOR_USER)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}