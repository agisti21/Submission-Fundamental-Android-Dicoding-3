package com.agisti.costumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserItems(
    var id : Int? = 0,
    var avatar: String?,
    var username: String?,
    var company: String?,
    var location: String?
):Parcelable