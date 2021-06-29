package com.agisti.costumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class FollowingItems (
    var avatar: String?,
    var username: String?
):Parcelable