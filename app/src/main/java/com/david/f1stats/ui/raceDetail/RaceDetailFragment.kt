package com.david.f1stats.ui.raceDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRaceDetailBinding
import com.david.f1stats.utils.CalendarHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RaceDetailFragment : Fragment(), RaceWeekendAdapter.CalendarListener {

    private var _binding: FragmentRaceDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RaceWeekendAdapter
    private val raceDetailViewModel: RaceDetailViewModel by viewModels()

    @Inject
    lateinit var calendarHelper: CalendarHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { raceDetailViewModel.start(it) }

        raceDetailViewModel.raceInfo.observe(viewLifecycleOwner) {
            binding.nameRace.text = it.competition
            binding.circuitRace.text = it.circuit
            binding.lapsRace.text = it.laps
        }

        setupRecyclerView()

        raceDetailViewModel.raceList.observe(viewLifecycleOwner) {
            it?.let { it1 -> ArrayList(it1) }?.let { it2 -> adapter.setItems(it2) }
        }

        raceDetailViewModel.addToCalendarEvent.observe(viewLifecycleOwner) { event ->
            event?.let {
                calendarHelper.addToCalendar(requireContext(), it)
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = RaceWeekendAdapter(this)
        binding.rvRaceWeekend.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaceWeekend.adapter = adapter
    }

    override fun onCalendarClicked(title: String, dateCalendar: Long) {
        val event = CalendarHelper.CalendarEvent(
            title = title,
            description = binding.nameRace.text.toString(),
            location = binding.circuitRace.text.toString(),
            startMillis = dateCalendar
        )
        raceDetailViewModel.onAddToCalendarRequested(event)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
