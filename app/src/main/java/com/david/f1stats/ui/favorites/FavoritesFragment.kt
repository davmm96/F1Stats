package com.david.f1stats.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.david.f1stats.R
import com.david.f1stats.ui.theme.F1StatsTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            F1StatsTheme {
                val races by viewModel.favoriteRaces.collectAsStateWithLifecycle()
                val isDeleted by viewModel.isDeleted.collectAsStateWithLifecycle()
                val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

                FavoritesScreen(
                    races = races,
                    isDeleted = isDeleted,
                    errorMessage = errorMessage,
                    onRemove = viewModel::deleteFavorite,
                    onNavigate = { idRace, country ->
                        findNavController().navigate(
                            R.id.action_navigation_favorites_to_raceResultFragment,
                            bundleOf("id" to idRace, "country" to country)
                        )
                    },
                    onDeletedConsumed = viewModel::clearIsDeleted,
                    onErrorConsumed = viewModel::clearErrorMessage
                )
            }
        }
    }
}
