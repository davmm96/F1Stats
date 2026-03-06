package com.david.f1stats.ui.circuits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import com.david.f1stats.ui.theme.F1StatsTheme
import com.david.f1stats.utils.Constants
import com.david.f1stats.utils.DialogHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CircuitsFragment : Fragment() {

    private val viewModel: CircuitsViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private val dialogHelper: DialogHelper by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            F1StatsTheme {
                val circuits by viewModel.circuitsList.collectAsStateWithLifecycle()
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

                CircuitsScreen(
                    circuits = circuits,
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    onMapClick = { url ->
                        if (url != Constants.IMAGE_NOT_FOUND) {
                            dialogHelper.showImageDialog(requireActivity(), imageLoader, url)
                        } else {
                            Toast.makeText(requireContext(), url, Toast.LENGTH_SHORT).show()
                        }
                    },
                    onErrorConsumed = viewModel::clearErrorMessage
                )
            }
        }
    }
}
