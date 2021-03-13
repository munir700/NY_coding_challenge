package com.codingchallenge

import com.data.local.models.ResponsePacket
import com.data.remote.models.Media
import com.data.remote.models.MediaMetadata
import com.data.remote.models.NewsArticle

object TestData {


    private fun getDesFacet(): List<String> = listOf(
        "Sexual Harassment",
        "Politics and Government",
        "Workplace Hazards and Violations",
        "Governors (US)"
    )

    private fun getOrgFacet(): List<String> = listOf(
        "Sexual Harassment",
        "Politics and Government",
        "Workplace Hazards and Violations",
        "Governors (US)"
    )

    private fun getPerFacet(): List<String> = listOf(
        "Cuomo, Andrew M",
        "Bennett, Charlotte (1995- )",
        "Boylan, Lindsey (1984- )"
    )

    private fun getGeoFacet(): List<String> = listOf(
        "New York State"
    )

    private fun getMedia(): List<Media> = listOf(
        Media(
            "image",
            "photo",
            "Charlotte Bennett said she decided to make her allegations public in part because she wanted to counter the way Mr. Cuomo “wields his power.”",
            "Elizabeth Frantz for The New York Times",
            1,
            getMediaMetadata()
        ),
    )

    private fun getMediaMetadata(): List<MediaMetadata> = listOf(
        MediaMetadata(
            "https://static01.nyt.com/images/2021/02/27/nyregion/00cuomo-harassmentNEW/00cuomo-harassmentNEW-thumbStandard.jpg",
            "Standard Thumbnail",
            75,
            75
        )
    )

    private fun getNewsArticle(): List<NewsArticle> = listOf(

        NewsArticle(
            "nyt://article/8b596b5a-3f2c-5956-bb46-235e1b89b9f9",
            "https://www.nytimes.com/2021/02/27/nyregion/cuomo-charlotte-bennett-sexual-harassment.html",
            100000007623064,
            100000007623064,
            "New York Times",
            "2021-02-27",
            "2021-03-01 05:51:32",
            "New York",
            "",
            "new york",
            "Sexual Harassment;Politics and Government;Workplace Hazards and Violations;Governors (US);Cuomo, Andrew M;Bennett, Charlotte (1995- );Boylan, Lindsey (1984- );New York State",
            null,
            "By Jesse McKinley",
            "Article",
            "Cuomo Is Accused of Sexual Harassment by a 2nd Former Aide",
            "The woman, 25, said that when they were alone in his office, Gov. Andrew Cuomo asked if she “had ever been with an older man.”",
            getDesFacet(),
            getOrgFacet(),
            getPerFacet(),
            getGeoFacet(),
            getMedia(),
            0

        )
    )

    fun getNewsArticles(): ResponsePacket<List<NewsArticle>> {
        return ResponsePacket<List<NewsArticle>>().apply {
            getNewsArticle()
        }
    }

}