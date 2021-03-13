package com.codingchallenge.ui.newsdetail

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.viewpager.widget.ViewPager
import com.codingchallenge.BR
import com.codingchallenge.R
import com.codingchallenge.databinding.ActivityNewsArticleDetailsBinding
import com.codingchallenge.ui.base.BaseActivity
import com.codingchallenge.ui.news.NewArticleFragment
import com.codingchallenge.ui.news.NewArticleViewModel
import com.data.remote.models.Media
import com.data.remote.models.MediaMetadata
import com.data.remote.models.NewsArticle

class NewsArticleDetailsActivity : BaseActivity<NewArticleViewModel, ActivityNewsArticleDetailsBinding>(R.layout.activity_news_article_details), PhotoSliderAdapter.OnClickListener {
    private var newsArticle: NewsArticle? = null
    private var mySliderPagerAdapter: PhotoSliderAdapter? = null

    override val viewModel: NewArticleViewModel by viewModels()

    override fun getBindingVariable(): Int {
        return BR.newsArticle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsArticle = intent.getParcelableExtra(NewArticleFragment.NEWARTICLE_INFO_INTENT_KEY)
        bindings.newArticle = newsArticle
        setPhotoSlider(newsArticle)
    }


    private fun setPhotoSlider(newsArticle: NewsArticle?) {
        val media: Media? = newsArticle?.media?.takeIf { it.isNotEmpty() }?.get(0)
        val mediaMetadata: List<MediaMetadata>? = if (media?.mediaMetadata != null && media.mediaMetadata!!.isNotEmpty()) media.mediaMetadata else listOfNotNull()
        if (mySliderPagerAdapter == null) {
            mySliderPagerAdapter = PhotoSliderAdapter(this, mediaMetadata, this)
            bindings.viewPager.adapter = mySliderPagerAdapter
            bindings.viewPager.offscreenPageLimit = 4
        } else {
            mySliderPagerAdapter?.setPhotos(mediaMetadata)
        }
        mySliderPagerAdapter?.setPhotoSliderCallBackListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startPostponedEnterTransition()
            }
        }
        bindings.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun onClose(v: View?) {
        onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }


    companion object {
        fun openActivityForResult(
            activity: Activity, newsArticle: NewsArticle?,
            thumb: View?, requestCode: Int, listingPosition: Int
        ) {
            val intent = Intent(activity, NewsArticleDetailsActivity::class.java)
            intent.putExtra(NewArticleFragment.NEWARTICLE_INFO_INTENT_KEY, newsArticle)
            intent.putExtra(NewArticleFragment.LISTING_POSITION, listingPosition)
            if (thumb != null) {
                val imgAnim = Pair.create<View?, String?>(
                    thumb, activity.resources.getString(R.string.STR_NEWS_DETAILS_TRANSITION)
                )
                val options: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        imgAnim
                    )
                activity.startActivityForResult(intent, requestCode, options.toBundle())
            } else {
                activity.startActivityForResult(intent, requestCode)
            }
        }
    }

    override fun onItemClick(position: Int, view: View?) {

    }
}