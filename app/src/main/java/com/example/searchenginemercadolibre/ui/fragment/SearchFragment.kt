package com.example.searchenginemercadolibre.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.searchenginemercadolibre.R
import com.example.searchenginemercadolibre.databinding.FragmentSearchBinding
import com.example.searchenginemercadolibre.ui.adapter.ItemListAdapter
import com.example.searchenginemercadolibre.ui.models.ItemView
import com.example.searchenginemercadolibre.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemListAdapter.OnItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemListAdapter
    private val listItem = mutableListOf<ItemView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initMenuProvider()
        setUpRecyclerView()
        setDataInit()
    }

    override fun onItemClick(item: ItemView) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(item)
        findNavController().navigate(action)
    }

    private fun initToolbar() {
        val navCollection = findNavController()
        val appBarConfiguration = AppBarConfiguration(navCollection.graph)
        binding.toolbarSearch.setupWithNavController(navCollection, appBarConfiguration)
    }

    private fun initMenuProvider() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.toolbarSearch)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = getString(R.string.buscar_en_mercado_libre)
                searchView.setOnCloseListener { true }
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (!newText.isNullOrEmpty()) {
                            val action =
                                SearchFragmentDirections.actionSearchFragmentToListProductsFragment(newText)
                            findNavController().navigate(action)
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.empy_query_search), Toast.LENGTH_SHORT).show()
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner)
    }

    private fun setUpRecyclerView() {
        adapter = ItemListAdapter(requireContext(), listItem, this)
        binding.rvListLastSearch.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListLastSearch.adapter = adapter
    }

    private fun setDataInit(){
        Glide.with(requireContext()).load("https://loremflickr.com/320/240/dog").placeholder(R.drawable.load).into(binding.ivBanner)
        val lastSearch = "Motorola%20G6"
        if(lastSearch != null){
            viewModel.fetchItemList(lastSearch)

            viewModel.itemList.observe(viewLifecycleOwner, Observer {
                listItem.clear()
                listItem.addAll(it)
                adapter.notifyDataSetChanged()
            })

            viewModel.totalItemsResponse.observe(viewLifecycleOwner, Observer {
                val totalResultSubtitle = "$it Favoritos"
                binding.tvSubtitle.text = totalResultSubtitle
            })

            viewModel.isLoading.observe(viewLifecycleOwner, Observer {
                binding.progressBar.isVisible = it
            })

            viewModel.error.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        } else {
            binding.tvSubtitle.text = getString(R.string.no_tiene_favoritos)
        }
    }
}