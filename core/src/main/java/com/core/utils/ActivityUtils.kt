package com.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

fun <T> Activity.launchActivity(
    context: Context,
    activityName: Class<T>,
    isFinish: Boolean = false,
    bundle: Bundle? = null
) {
    val intent = Intent(context, activityName)
    bundle?.let {
        intent.putExtras(bundle)
    }
    this.startActivity(intent)
    if (isFinish)
        this.finish()
}