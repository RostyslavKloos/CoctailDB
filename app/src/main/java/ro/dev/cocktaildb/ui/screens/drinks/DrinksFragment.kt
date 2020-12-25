package ro.dev.cocktaildb.ui.screens.drinks

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import ro.dev.cocktaildb.R
import ro.dev.cocktaildb.data.model.drink.DrinksListResponseModel
import ro.dev.cocktaildb.data.model.filter.CategoryDrink
import ro.dev.cocktaildb.databinding.DrinksFragmentBinding
import ro.dev.cocktaildb.ui.screens.filters.FiltersAdapter
import ro.dev.cocktaildb.utills.Resource

class DrinksFragment : Fragment() {

    private var _binding: DrinksFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DrinksFragmentArgs by navArgs()
    private lateinit var viewModel: DrinksViewModel

    private lateinit var adapter: DrinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DrinksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DrinksViewModel::class.java)

        getFilters()
        setHasOptionsMenu(true)
        setupRecyclerView()
        //drinksRequests()
    }

    //todo 1  (finished)
    private fun getFilters() {
        if (viewModel.filterList.toTypedArray().toList().isNullOrEmpty()) {
            viewModel.filters.observe(viewLifecycleOwner, { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        resource.data?.let { categoriesResponse ->
                            //todo 2 (finished)
                            val filterMap = mutableMapOf<String, Boolean>()
                            categoriesResponse.drinks.forEach { categoryDrink ->
                                filterMap[categoryDrink.strCategory] = true
                            }
                            viewModel.filterMap.putAll(filterMap)


                            //todo 3 (partially finished)
                            val arrayList = arrayListOf<CategoryDrink>()
                            arrayList.addAll(categoriesResponse.drinks)

                            viewModel.setFilterList(arrayList)
                            viewModel.filterList.addAll(arrayList)
                            drinksRequests()
                        }
                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    Resource.Status.LOADING -> {
                    }
                }
            })
        } else drinksRequests()
    }

    private fun setupRecyclerView() {
        adapter = DrinksAdapter(emptyList(), emptyList())
        binding.rvDrinksList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDrinksList.adapter = adapter
    }

    private fun drinksRequests() {
        CoroutineScope(Dispatchers.Main).launch {
            //viewModel.filterList.value?.toTypedArray()?.let {
            viewModel.filterList.toTypedArray().let {
                binding.pbDrinks.visibility = View.VISIBLE
                val list = viewModel.multipleDrinksRequest(it)
                adapter = DrinksAdapter(list, it.toList())
                binding.rvDrinksList.adapter = adapter
                binding.pbDrinks.visibility = View.GONE

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            findNavController().navigate(R.id.action_drinksFragment_to_filtersFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}


