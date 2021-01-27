package org.wit.sites.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class SiteModel(var id: Long = 0,
                     var fbId: String = "",
                     var jsonUserId: Long = 0,
                     var title: String = "",
                     var description: String = "",
                     var image1: String = "",
                     var image2: String = "",
                     var image3: String = "",
                     var image4: String = "",
                     var visited: Boolean = false,
                     var visitedDate: String = "",
                     var notes: String ="",
                     var location: Location = Location()) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable