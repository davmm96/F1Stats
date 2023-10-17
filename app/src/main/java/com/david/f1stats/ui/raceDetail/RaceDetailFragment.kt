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

    @Inject
    lateinit var calendarHelper: CalendarHelper

    private var _binding: FragmentRaceDetailBinding? = null
    private val binding get() = _binding!!
    private val adapter: RaceWeekendAdapter by lazy { RaceWeekendAdapter(this) }
    private val raceDetailViewModel: RaceDetailViewModel by viewModels()

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
        arguments?.getInt("id")?.let { raceDetailViewModel.loadData(it) }

        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvRaceWeekend.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRaceWeekend.adapter = adapter
    }

    private fun initObservers(){
        raceDetailViewModel.raceInfo.observe(viewLifecycleOwner) { raceDetail ->
            binding.apply {
                nameRace.text = raceDetail.competition
                circuitRace.text = raceDetail.circuit
                lapsRace.text = raceDetail.laps
            }
        }

        raceDetailViewModel.raceList.observe(viewLifecycleOwner) { raceList ->
            raceList?.let { adapter.setItems(ArrayList(it)) }
        }

        raceDetailViewModel.addToCalendarEvent.observe(viewLifecycleOwner) { event ->
            event?.let { calendarHelper.addToCalendar(requireContext(), it) }
        }
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
}
