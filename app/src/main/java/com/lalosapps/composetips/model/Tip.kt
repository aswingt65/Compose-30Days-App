package com.lalosapps.composetips.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Tip(
    @StringRes val dayRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descRes: Int,
    @DrawableRes val imageRes: Int
)
