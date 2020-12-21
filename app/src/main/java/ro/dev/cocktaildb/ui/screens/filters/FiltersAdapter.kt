package ro.dev.cocktaildb.ui.screens.filters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.dev.cocktaildb.data.model.filter.CategoryDrink
import ro.dev.cocktaildb.databinding.ItemCategoryBinding

class FiltersAdapter(private val categoriesList: List<CategoryDrink>, private val listener: SelectItemListener): RecyclerView.Adapter<CategoryViewHolder>() {

    interface SelectItemListener {
        fun onClickCategory(view:View, categoryDrink: CategoryDrink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val binding: ItemCategoryBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =  holder.bind(categoriesList[position])

    override fun getItemCount(): Int = categoriesList.size
}

class CategoryViewHolder(private val itemBinding: ItemCategoryBinding, private val listener: FiltersAdapter.SelectItemListener):
    RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{

    init {
        itemBinding.root.setOnClickListener(this)
    }

    private lateinit var categoryDrink: CategoryDrink

    fun bind(categoryDrink: CategoryDrink) {
        this.categoryDrink = categoryDrink
        itemBinding.tvCategoryName.text = categoryDrink.strCategory
    }

    override fun onClick(v: View?) {
        listener.onClickCategory(itemBinding.ivSelectCategory, categoryDrink)
    }
}
