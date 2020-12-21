package ro.dev.cocktaildb.data.model.drink

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String
): Parcelable