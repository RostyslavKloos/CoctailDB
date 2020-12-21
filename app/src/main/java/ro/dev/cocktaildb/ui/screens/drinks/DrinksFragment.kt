package ro.dev.cocktaildb.ui.screens.drinks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.*
import ro.dev.cocktaildb.R
import ro.dev.cocktaildb.data.model.drink.DrinksListResponseModel
import ro.dev.cocktaildb.databinding.DrinksFragmentBinding

class DrinksFragment : Fragment() {

    private var _binding: DrinksFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: DrinksFragmentArgs by navArgs()
    private val viewModel: DrinksViewModel by viewModels()

    private lateinit var adapter: DrinksAdapter
    private lateinit var drinksListResponse: ArrayList<DrinksListResponseModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DrinksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupRecyclerView()
        drinksRequests()
    }

    private fun setupRecyclerView() {
        adapter = DrinksAdapter(emptyList(), emptyList())
        binding.rvDrinksList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDrinksList.adapter = adapter
    }

    private fun drinksRequests() {
        drinksListResponse = ArrayList(emptyList())
        val categories = args.Categories

        CoroutineScope(Dispatchers.Main).launch {
            categories?.let {
                binding.pbDrinks.visibility = View.VISIBLE
                val list = viewModel.multipleDrinksRequest(categories)
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


