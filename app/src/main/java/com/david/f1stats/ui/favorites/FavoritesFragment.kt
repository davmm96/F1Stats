package com.david.f1stats.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.FavoriteItemListener, FavoritesAdapter.FavoriteNavListener {
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

        favoriteRacesViewModel.isDeleted.observe(viewLifecycleOwner) { isDeleted ->
            if(isDeleted)
                Toast.makeText(context, getString(R.string.favorite_removed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = FavoritesAdapter(this, this)
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
    }

    override fun removeFavorite(idRace: Int) {
        favoriteRacesViewModel.deleteFavorite(idRace)
    }
    override fun onNavClicked(idRace: Int, country: String) {
        findNavController().navigate(
            R.id.action_navigation_favorites_to_raceResultFragment,
            bundleOf("id" to idRace, "country" to country)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}