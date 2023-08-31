package com.david.f1stats.ui.races

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.david.f1stats.databinding.FragmentRacesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_races.view.btnGo

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

        val btnGo = root.btnGo

        btnGo.setOnClickListener{
            findNavController()
                .navigate(RacesFragmentDirections.actionNavigationRacesToRaceDetailFragment(idRace = "Carrera 1"))
        }
        racesViewModel.onCreate()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
