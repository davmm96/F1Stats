package com.david.f1stats.ui.circuits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentCircuitsBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CircuitsFragment : Fragment(), CircuitsAdapter.CircuitItemListener {

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var dialogHelper: DialogHelper

    private var _binding: FragmentCircuitsBinding? = null
    private val binding get() = _binding!!
    private val circuitViewModel: CircuitsViewModel by viewModels()
    private val adapter by lazy { CircuitsAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCircuitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers(){
        circuitViewModel.circuitsList.observe(viewLifecycleOwner) { listCircuits ->
            listCircuits?.let { adapter.setItems(ArrayList(it)) }
        }

        circuitViewModel.isLoading.observe(viewLifecycleOwner){ isLoading ->
            binding.progressBar.isVisible = isLoading
        }
    }

    private fun initRecyclerView() {
        binding.rvCircuits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCircuits.adapter = adapter
    }

    override fun onClickedCircuit(imageUrl: String) {
        if(imageUrl != Constants.IMAGE_NOT_FOUND){
            dialogHelper.showImageDialog(requireActivity(), picasso, imageUrl)
        }
        else{
            Toast.makeText(this.context, imageUrl, Toast.LENGTH_SHORT).show()
        }
    }
}
