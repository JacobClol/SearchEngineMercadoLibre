package com.example.searchenginemercadolibre.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.example.searchenginemercadolibre.databinding.FragmentDetailProductBinding
import com.example.searchenginemercadolibre.domain.models.AttributesModel
import com.example.searchenginemercadolibre.ui.adapter.AttributeAdapter
import com.example.searchenginemercadolibre.ui.adapter.ViewPagerAdapter
import com.example.searchenginemercadolibre.ui.viewmodel.DetailItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private val args: DetailProductFragmentArgs by navArgs()
    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailItemViewModel>()

    private lateinit var adapter: AttributeAdapter
    private val listAttribute = mutableListOf<AttributesModel>()

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val listPhotos = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setUpRecyclerView()
        setUpViewPager()
        setDataInit()
    }

    private fun initToolbar() {
        val navCollection = findNavController()
        val appBarConfiguration = AppBarConfiguration(navCollection.graph)
        binding.toolbar.setupWithNavController(navCollection, appBarConfiguration)
        binding.toolbar.inflateMenu(R.menu.favorite)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favoriteAction -> {
                    if (args.item.isSaveDB) {
                        it.icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_favorite_border_24
                        )
                        viewModel.deleteItemDB(args.item)
                    } else {
                        it.icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_favorite_24
                        )
                        viewModel.insertItemDB(args.item)
                    }
                    true
                }
                else -> false
            }
        }
        if (args.item.isSaveDB) {
            val item = binding.toolbar.menu.findItem(R.id.favoriteAction)
            item.icon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun setUpRecyclerView() {
        adapter = AttributeAdapter(requireContext(), listAttribute)
        binding.rvAttribute.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAttribute.adapter = adapter
    }

    private fun setUpViewPager() {
        viewPagerAdapter = ViewPagerAdapter(requireContext(), listPhotos)
        binding.vpPhotos.adapter = viewPagerAdapter
    }

    private fun setDataInit() {
        viewModel.itemDetail.observe(viewLifecycleOwner, Observer {
            with(binding) {
                try {
                    tvTitleItem.text = it.title
                    val doublePrice = it.price.toDouble()
                    val formatter = DecimalFormat("#,###.00")
                    val price = "$ ${formatter.format(doublePrice)}"
                    tvPrice.text = price
                    val sellerStatus = "Estatus Vendedor: ${it.sellerStatus}"
                    tvSelletSatus.text = sellerStatus
                    val availableQuantity = "Disponibles: ${it.availableQuantity}"
                    tvAvailableQuantity.text = availableQuantity
                    val soldQuantity = "Venditos: ${it.soldQuantity}"
                    tvSoldQuantity.text = soldQuantity
                    tvCondition.text = it.condition
                    if (it.freeShipping) {
                        val freeShipping = "FreeShipping"
                        tvFreeShiping.text = freeShipping
                    }
                    val stateName = "Estado producto: ${it.stateName}"
                    tvStateName.text = stateName
                    val cityName = "Ciudad producto: ${it.cityName}"
                    tvCityName.text = cityName
                    it.attributes?.let { attributes -> listAttribute.addAll(attributes) }
                    adapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Falla en la carga de datos",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })

        viewModel.succesDB.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        viewModel.pictures.observe(viewLifecycleOwner) {
            listPhotos.addAll(it)
            viewPagerAdapter.notifyDataSetChanged()
        }

        viewModel.attributeDetail.observe(viewLifecycleOwner) {
            val sizePreviesList = listAttribute.size
            listAttribute.addAll(it)
            adapter.notifyItemRangeInserted(sizePreviesList -1, it.size)
        }
    }
}