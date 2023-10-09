package com.david.f1stats.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.FavoriteItemListener {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoritesAdapter
    private val favoriteRacesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        favoriteRacesViewModel.onCreate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        favoriteRacesViewModel.favoriteRaceList.observe(viewLifecycleOwner) { list->
            list?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
            if(list.isNullOrEmpty()){
                binding.tvNoFavorites.isVisible = adapter.itemCount == 0
            }
            else{
                binding.tvNoFavorites.isVisible = false
            }
        }

        favoriteRacesViewModel.isLoading.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter(this)
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
    }


    override fun removeFavorite(idRace: Int) {
        favoriteRacesViewModel.deleteFavorite(idRace)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}