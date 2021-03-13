package com.data.remote.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json


data class NewsArticle(
    @Json(name = "uri")
    val uri: String?,

    @Json(name = "url")
    val url: String?,

    @Json(name = "id")
    val id: Long,

    @Json(name = "asset_id")
    val assetId: Long,

    @Json(name = "source")
    val source: String?,

    @Json(name = "published_date")
    val publishedDate: String?,

    @Json(name = "updated")
    val updated: String?,

    @Json(name = "section")
    val section: String?,

    @Json(name = "subsection")
    val subsection: String?,

    @Json(name = "nytdsection")
    val nytdsection: String?,

    @Json(name = "adx_keywords")
    val adxKeywords: String?,

    @Json(name = "column")
    val column: String?,

    @Json(name = "byline")
    val byline: String?,

    @Json(name = "type")
    val type: String?,

    @Json(name = "title")
    val title: String?,

    @Json(name = "abstract")
    val abstract: String?,

    @Json(name = "des_facet")
    val desFacet: List<String>? = null,

    @Json(name = "org_facet")
    val orgFacet: List<String>? = null,

    @Json(name = "per_facet")
    val perFacet: List<String>? = null,

    @Json(name = "geo_facet")
    val geoFacet: List<String>? = null,

    @Json(name = "media")
    val media: List<Media>? = null,

    @Json(name = "eta_id")
    val etaId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.createTypedArrayList(Media),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeString(url)
        parcel.writeLong(id)
        parcel.writeLong(assetId)
        parcel.writeString(source)
        parcel.writeString(publishedDate)
        parcel.writeString(updated)
        parcel.writeString(section)
        parcel.writeString(subsection)
        parcel.writeString(nytdsection)
        parcel.writeString(adxKeywords)
        parcel.writeString(column)
        parcel.writeString(byline)
        parcel.writeString(type)
        parcel.writeString(title)
        parcel.writeString(abstract)
        parcel.writeStringList(desFacet)
        parcel.writeStringList(orgFacet)
        parcel.writeStringList(perFacet)
        parcel.writeStringList(geoFacet)
        parcel.writeTypedList(media)
        parcel.writeInt(etaId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsArticle> {
        override fun createFromParcel(parcel: Parcel): NewsArticle {
            return NewsArticle(parcel)
        }

        override fun newArray(size: Int): Array<NewsArticle?> {
            return arrayOfNulls(size)
        }
    }
}
