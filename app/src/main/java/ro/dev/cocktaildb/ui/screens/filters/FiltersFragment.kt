package ro.dev.cocktaildb.ui.screens.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.dev.cocktaildb.data.model.filter.CategoryDrink
import ro.dev.cocktaildb.databinding.FiltersFragmentBinding
import ro.dev.cocktaildb.ui.screens.drinks.DrinksViewModel

class FiltersFragment : Fragment(), FiltersAdapter.SelectItemListener {

    private var _binding: FiltersFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DrinksViewModel
    private lateinit var adapter: FiltersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FiltersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DrinksViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupViews()
    }

    private fun setupViews() {
        binding.btnFilters.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                findNavController().navigate(
                    FiltersFragmentDirections.actionFiltersFragmentToDrinksFragment(
                        viewModel.filterList.toTypedArray()
                    )
                )
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = FiltersAdapter(emptyList(), this, viewModel)
        binding.rvFilters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilters.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.filterListt.observe(viewLifecycleOwner, {
            binding.pbFiltersProgress.visibility = View.VISIBLE
            adapter = FiltersAdapter(it.toList(), this, viewModel)
            binding.rvFilters.adapter = adapter
            binding.pbFiltersProgress.visibility = View.GONE
        })
    }

    override fun onClickCategory(view: View, categoryDrink: CategoryDrink) {
        if (view.visibility == View.INVISIBLE) {
            view.visibility = View.VISIBLE

                viewModel.filterList.add(categoryDrink)
                viewModel.filterMap.put(categoryDrink.strCategory, true)

        } else if (view.visibility == View.VISIBLE) {
            view.visibility = View.INVISIBLE
                viewModel.filterList.remove(categoryDrink)
                viewModel.filterMap.remove(categoryDrink.strCategory)
        }
    }
}