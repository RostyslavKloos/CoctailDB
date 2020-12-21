package ro.dev.cocktaildb.data.model.drink

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinksListResponseModel(
    val drinks: List<Drink>
): Parcelable