package com.david.f1stats.ui.races

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.david.f1stats.databinding.FragmentRacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RacesFragment : Fragment() {

    private var _binding: FragmentRacesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val racesViewModel =
            ViewModelProvider(this)[RacesViewModel::class.java]

        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        racesViewModel.onCreate()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
