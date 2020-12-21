package ro.dev.cocktaildb.ui.screens.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ro.dev.cocktaildb.data.model.filter.CategoryDrink
import ro.dev.cocktaildb.databinding.FiltersFragmentBinding
import ro.dev.cocktaildb.utills.Resource
import ro.dev.cocktaildb.utills.SharedManager

class FiltersFragment : Fragment(), FiltersAdapter.SelectItemListener {

    private var _binding: FiltersFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FiltersViewModel by viewModels()
    private lateinit var adapter: FiltersAdapter
    private lateinit var filterList: ArrayList<CategoryDrink>
    private lateinit var sharedManager: SharedManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FiltersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterList = ArrayList(emptyList())

        setupRecyclerView()
        setupObservers()
        setupViews()
        //checkSelectedFilters()
    }

    private fun setupViews() {
        binding.btnFilters.setOnClickListener {
            findNavController().navigate(
                FiltersFragmentDirections.actionFiltersFragmentToDrinksFragment(
                    filterList.toTypedArray()
                )
            )
        }
    }

    private fun setupRecyclerView() {
        adapter = FiltersAdapter(emptyList(), this)
        binding.rvFilters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilters.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.filters.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { categoriesResponse ->
                        adapter = FiltersAdapter(categoriesResponse.drinks, this)
                        binding.rvFilters.adapter = adapter
                        filterList.addAll(categoriesResponse.drinks)
                        binding.pbFiltersProgress.visibility = View.GONE
                    }
                }
                Resource.Status.ERROR -> {
                    binding.pbFiltersProgress.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    binding.pbFiltersProgress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun checkSelectedFilters() {
        sharedManager.filterStateFlow.asLiveData().observe(viewLifecycleOwner, { map ->
            binding.rvFilters.children.forEach { view ->
                if (map.containsKey(view.tv_categoryName.text)) {
                    if (map.getValue(view.tv_categoryName.text.toString())) {
                        view.iv_selectCategory.visibility = View.VISIBLE
                    } else view.iv_selectCategory.visibility = View.GONE
                }
            }
        })
    }

    override fun onClickCategory(view: View, categoryDrink: CategoryDrink) {
        if (view.visibility == View.INVISIBLE) {
            view.visibility = View.VISIBLE
            val filterMap = mutableMapOf<String, Boolean>()
            filterMap[categoryDrink.strCategory] = true
            MainScope().launch {
                sharedManager.storeFilterState(filterMap)
            }
            filterList.add(categoryDrink)
        } else {
            view.visibility = View.INVISIBLE
//            val filterMap = mutableMapOf<String, Boolean>()
//            filterMap[categoryDrink.strCategory] = false
//            MainScope().launch {
//                sharedManager.storeFilterState(filterMap)
//            }
            filterList.remove(categoryDrink)
        }
    }
}