package ro.dev.cocktaildb.ui.screens.drinks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ro.dev.cocktaildb.data.model.drink.DrinksListResponseModel
import ro.dev.cocktaildb.databinding.ItemDrinkProductBinding

class DrinkItemAdapter(private val drink: DrinksListResponseModel):
    RecyclerView.Adapter<DrinkItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkItemViewHolder {
        val binding: ItemDrinkProductBinding = ItemDrinkProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinkItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinkItemViewHolder, position: Int) {
        holder.tvDescription.text = drink.drinks[position].strDrink
        Glide.with(holder.ivDrink)
            .load(drink.drinks[position].strDrinkThumb)
            .into(holder.ivDrink)
    }

    override fun getItemCount(): Int = drink.drinks.size
}

class DrinkItemViewHolder(itemBinding: ItemDrinkProductBinding):
    RecyclerView.ViewHolder(itemBinding.root) {
    val tvDescription = itemBinding.tvDrinkDescription
    val ivDrink = itemBinding.ivDrinkImage
}
