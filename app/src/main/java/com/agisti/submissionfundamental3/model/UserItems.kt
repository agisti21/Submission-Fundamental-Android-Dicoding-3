package com.agisti.submissionfundamental3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserItems(
    var id : Int?,
    var avatar: String?,
    var username: String?,
    var company: String?,
    var location: String?
):Parcelable