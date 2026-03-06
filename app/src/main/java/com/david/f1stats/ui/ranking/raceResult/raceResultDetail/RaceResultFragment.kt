package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRaceResultBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RaceResultFragment : Fragment() {

    private val raceResultViewModel: RaceResultViewModel by viewModel()
    private val adapter: RaceResultAdapter by lazy { RaceResultAdapter() }

    private var _binding: FragmentRaceResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { raceResultViewModel.fetchRaceResult(it) }
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResults.adapter = adapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            raceResultViewModel.raceResult.collect { raceResultList ->
                adapter.setItems(ArrayList(raceResultList))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            raceResultViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    raceResultViewModel.clearErrorMessage()
                }
            }
        }
    }
}
