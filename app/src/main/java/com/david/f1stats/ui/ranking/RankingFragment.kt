package com.david.f1stats.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRankingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RankingFragment : Fragment(), RankingAdapter.RankingItemListener {

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RankingAdapter
    private val rankingViewModel: RankingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rankingViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        rankingViewModel.rankingList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        rankingViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = RankingAdapter(this)
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickedDriver(driverId: Int) {
        Toast.makeText(this.context, "Piloto seleccionado-> $driverId", Toast.LENGTH_SHORT).show()
    }
}
