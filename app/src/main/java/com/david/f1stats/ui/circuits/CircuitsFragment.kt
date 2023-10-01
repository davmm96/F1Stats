package com.david.f1stats.ui.circuits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentCircuitsBinding
import com.david.f1stats.ui.ranking.RankingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CircuitsFragment : Fragment(), CircuitsAdapter.CircuitItemListener {

    private var _binding: FragmentCircuitsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CircuitsAdapter
    private val circuitViewModel: CircuitsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCircuitsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        circuitViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        circuitViewModel.circuitsList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        circuitViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = CircuitsAdapter(this)
        binding.rvCircuits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCircuits.adapter = adapter
    }

    override fun onClickedCircuit(circuitId: Int) {
        Toast.makeText(this.context, "Circuito seleccionado-> $circuitId", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
