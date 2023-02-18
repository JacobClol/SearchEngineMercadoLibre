package com.example.searchenginemercadolibre.ui.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchenginemercadolibre.R
import com.example.searchenginemercadolibre.databinding.FragmentListProductsBinding
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.ui.adapter.ItemListAdapter
import com.example.searchenginemercadolibre.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListProductsFragment : Fragment(), ItemListAdapter.OnItemClickListener {

    private var _binding: FragmentListProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemListAdapter
    private val listItem = mutableListOf<Item>()

    private val viewModel:SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initMenuProvider()
        setUpRecyclerView()
        setDataInit()
    }

    override fun onItemClick(item: Item) {
       val action = ListProductsFragmentDirections.actionListProductsFragmentToDetailProductFragment(item)
       findNavController().navigate(action)
    }

    private fun initToolbar() {
        val navCollection = findNavController()
        val appBarConfiguration = AppBarConfiguration(navCollection.graph)
        binding.toolbarSearch.setupWithNavController(navCollection, appBarConfiguration)
    }

    private fun initMenuProvider() {
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.app_bar_search)
                val searchView: SearchView = searchItem.actionView as SearchView
                searchView.queryHint = getString(R.string.search_in_mercado_libre)
                searchView.setOnCloseListener { true }
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (!query.isNullOrEmpty()) {
                            viewModel.fetchItemList(query)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.empy_query_search),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        hideKeyboard()
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }
        binding.toolbarSearch.addMenuProvider(menuProvider, viewLifecycleOwner)
    }

    private fun setUpRecyclerView() {
        adapter = ItemListAdapter(requireContext(), listItem, this)
        binding.rvListLastSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListLastSearch.adapter = adapter
    }

    private fun setDataInit() {
        viewModel.itemList.observe(viewLifecycleOwner, Observer {
            listItem.clear()
            listItem.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.totalItemsResponse.observe(viewLifecycleOwner, Observer {
            val totalResultSubtitle = "$it Resultados"
            binding.tvResult.text = totalResultSubtitle
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}