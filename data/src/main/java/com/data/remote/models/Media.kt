package com.data.remote.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Media(
    @Json(name = "type")
    val type: String?,
    @Json(name = "subtype")
    val subtype: String?,
    @Json(name = "caption")
    val caption: String?,
    @Json(name = "copyright")
    val copyright: String?,
    @Json(name = "approved_for_syndication")
    val approvedForSyndication: Int,
    @Json(name = "media-metadata")
    val mediaMetadata: List<MediaMetadata>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createTypedArrayList(MediaMetadata)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(subtype)
        parcel.writeString(caption)
        parcel.writeString(copyright)
        parcel.writeInt(approvedForSyndication)
        parcel.writeTypedList(mediaMetadata)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Media> {
        override fun createFromParcel(parcel: Parcel): Media {
            return Media(parcel)
        }

        override fun newArray(size: Int): Array<Media?> {
            return arrayOfNulls(size)
        }
    }
}
