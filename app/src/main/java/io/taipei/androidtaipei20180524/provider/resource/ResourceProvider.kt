package io.taipei.androidtaipei20180524.provider.resource

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import io.taipei.androidtaipei20180524.provider.resource.AppResource

/**
 * Concrete implementation of the [AppResource] interface.
 * Must be init when [Application.onCreate]
 */
@SuppressLint("StaticFieldLeak")
class ResourceProvider(
        var application: Application
) : AppResource {

    override fun resources(): Resources = application.resources

    override fun getString(stringRes: Int): String = application.getString(stringRes)

    override fun getColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(application, colorRes)

    override fun getDrawable(@DrawableRes drawableRes: Int): Drawable = ContextCompat.getDrawable(application, drawableRes)!!

}
