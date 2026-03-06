package com.david.f1stats.ui.circuits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.ImageLoader
import com.david.f1stats.databinding.FragmentCircuitsBinding
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CircuitsFragment : Fragment(), CircuitsAdapter.CircuitItemListener {

    private val imageLoader: ImageLoader by inject()
    private val dialogHelper: DialogHelper by inject()
    private val circuitViewModel: CircuitsViewModel by viewModel()
    private val adapter by lazy { CircuitsAdapter(this) }

    private var _binding: FragmentCircuitsBinding? = null
    private val binding get() = _binding!!

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

    private fun initRecyclerView() {
        binding.rvCircuits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCircuits.adapter = adapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            circuitViewModel.circuitsList.collect { listCircuits ->
                adapter.setItems(ArrayList(listCircuits))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            circuitViewModel.isLoading.collect { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            circuitViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    circuitViewModel.clearErrorMessage()
                }
            }
        }
    }

    override fun onClickedCircuit(imageUrl: String) {
        if (imageUrl != Constants.IMAGE_NOT_FOUND) {
            dialogHelper.showImageDialog(requireActivity(), imageLoader, imageUrl)
        } else {
            Toast.makeText(this.context, imageUrl, Toast.LENGTH_SHORT).show()
        }
    }
}
