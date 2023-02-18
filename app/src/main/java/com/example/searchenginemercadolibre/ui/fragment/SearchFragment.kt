package com.example.searchenginemercadolibre.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
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
import com.example.searchenginemercadolibre.domain.models.Item
import com.example.searchenginemercadolibre.ui.adapter.ItemListAdapter
import com.example.searchenginemercadolibre.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), ItemListAdapter.OnItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ItemListAdapter
    private val listItem = mutableListOf<Item>()

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

    override fun onItemClick(item: Item) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(item)
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
                        query?.let {
                            val action =
                                SearchFragmentDirections.actionSearchFragmentToListProductsFragment(
                                    it
                                )
                            findNavController().navigate(action)
                        }
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
        Glide.with(requireContext()).load("https://loremflickr.com/320/240/dog")
            .placeholder(R.drawable.load).into(binding.ivBanner)

        viewModel.getItemFromDataBase()

        viewModel.itemList.observe(viewLifecycleOwner, Observer {
            listItem.clear()
            listItem.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.totalItemsResponse.observe(viewLifecycleOwner, Observer {
            if (it.equals("0")) {
                binding.tvSubtitle.text = getString(R.string.without_favorites)
            } else {
                val totalResultSubtitle = "$it Favoritos"
                binding.tvSubtitle.text = totalResultSubtitle
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }
}