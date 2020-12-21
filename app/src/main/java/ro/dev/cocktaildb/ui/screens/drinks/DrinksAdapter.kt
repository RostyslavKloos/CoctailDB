package ro.dev.cocktaildb.ui.screens.drinks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.dev.cocktaildb.data.model.drink.DrinksListResponseModel
import ro.dev.cocktaildb.data.model.filter.CategoryDrink
import ro.dev.cocktaildb.databinding.ItemDrinksBinding

class DrinksAdapter(private val drinksList: List<DrinksListResponseModel>, private val categoryName: List<CategoryDrink>):
    RecyclerView.Adapter<DrinksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        val binding: ItemDrinksBinding = ItemDrinksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrinksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) = holder.bind(drinksList[position], categoryName[position])

    override fun getItemCount(): Int = drinksList.size
}

class DrinksViewHolder(private val itemBinding: ItemDrinksBinding):
    RecyclerView.ViewHolder(itemBinding.root){

    fun bind(categoryDrink: DrinksListResponseModel, categoryName: CategoryDrink) {
        itemBinding.tvDrinkTitle.text = categoryName.strCategory

        val childLayoutManager = LinearLayoutManager(itemBinding.rvDrinksList.context, LinearLayoutManager.VERTICAL,false)
        itemBinding.rvDrinksList.apply {
            layoutManager = childLayoutManager
            adapter = DrinkItemAdapter(categoryDrink)
            setRecycledViewPool(recycledViewPool)
        }
    }
}
