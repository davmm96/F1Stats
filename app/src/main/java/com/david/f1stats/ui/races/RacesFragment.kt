package com.david.f1stats.ui.races

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.david.f1stats.databinding.FragmentRacesBinding

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
            ViewModelProvider(this).get(RacesViewModel::class.java)

        _binding = FragmentRacesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRaces
        racesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
