package com.example.searchenginemercadolibre.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.searchenginemercadolibre.R
import com.example.searchenginemercadolibre.databinding.FragmentDetailProductBinding
import java.text.DecimalFormat

class DetailProductFragment : Fragment() {

    private val args: DetailProductFragmentArgs by navArgs()
    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!

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
        setDataInit()
    }

    private fun initToolbar() {
        val navCollection = findNavController()
        val appBarConfiguration = AppBarConfiguration(navCollection.graph)
        binding.toolbarSearch.setupWithNavController(navCollection, appBarConfiguration)
    }

    private fun setDataInit() {
        with(binding) {
            try {
                Glide.with(requireContext()).load(args.item.thumbnail).placeholder(R.drawable.load)
                    .into(ivPhotos)
                tvTitleItem.text = args.item.title
                val doublePrice = args.item.price.toDouble()
                val formatter = DecimalFormat("#,###.00")
                val price = "$ ${formatter.format(doublePrice)}"
                tvPrice.text = price
                tvDescription.text = args.item.condition
                tvAttributes.text = args.item.attributes[0].name
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Falla en la carga de datos", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}