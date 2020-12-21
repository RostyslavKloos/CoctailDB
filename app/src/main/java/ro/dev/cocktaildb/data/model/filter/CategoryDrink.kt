package ro.dev.cocktaildb.data.model.filter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryDrink(
    val strCategory: String
): Parcelable