package com.david.f1stats.ui.ranking.raceResult.raceResultDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRaceResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceResultFragment : Fragment() {

    private var _binding: FragmentRaceResultBinding? = null
    private val binding get() = _binding!!
    private val adapter: RaceResultAdapter by lazy { RaceResultAdapter() }
    private val raceResultViewModel: RaceResultViewModel by viewModels()

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

    private fun initRecyclerView() {
        binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
        binding.rvResults.adapter = adapter
    }

    private fun initObservers() {
        raceResultViewModel.raceResult.observe(viewLifecycleOwner) { raceResultList ->
            raceResultList?.let { adapter.setItems(ArrayList(it)) }
        }

        raceResultViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                raceResultViewModel.clearErrorMessage()
            }
        }
    }
}