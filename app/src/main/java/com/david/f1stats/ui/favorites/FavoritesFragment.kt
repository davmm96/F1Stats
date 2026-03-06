package com.david.f1stats.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.R
import com.david.f1stats.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), FavoritesAdapter.FavoriteItemListener,
    FavoritesAdapter.FavoriteNavListener {

    private val favoriteRacesViewModel: FavoritesViewModel by viewModel()
    private val adapter: FavoritesAdapter by lazy { FavoritesAdapter(this, this) }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
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
        binding.rvRaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaces.adapter = adapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteRacesViewModel.favoriteRaces.collect { list ->
                adapter.setItems(ArrayList(list))
                val empty = list.isEmpty()
                binding.tvNoFavorites.isVisible = empty
                binding.tvNoFavoriteSubtitle.isVisible = empty
                binding.ivNoFavorites.isVisible = empty
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteRacesViewModel.isDeleted.collect { isDeleted ->
                if (isDeleted) {
                    Toast.makeText(
                        context,
                        getString(R.string.favorite_removed),
                        Toast.LENGTH_SHORT
                    ).show()
                    favoriteRacesViewModel.clearIsDeleted()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteRacesViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    favoriteRacesViewModel.clearErrorMessage()
                }
            }
        }
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
}
