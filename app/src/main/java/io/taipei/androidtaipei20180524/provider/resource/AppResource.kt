package io.taipei.androidtaipei20180524.provider.resource

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

/**
 * Resolves application's [android.content.res.Resources].
 */
interface AppResource {

    fun resources(): Resources

    fun getString(@StringRes stringRes: Int): String

    fun getColor(@ColorRes colorRes: Int): Int

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable?
}
