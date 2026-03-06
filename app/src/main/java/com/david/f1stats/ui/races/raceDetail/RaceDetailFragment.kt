package com.david.f1stats.ui.races.raceDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.david.f1stats.databinding.FragmentRaceDetailBinding
import com.david.f1stats.utils.CalendarHelper
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RaceDetailFragment : Fragment(), RaceWeekendAdapter.CalendarListener {

    private val calendarHelper: CalendarHelper by inject()
    private val raceDetailViewModel: RaceDetailViewModel by viewModel()
    private val adapter: RaceWeekendAdapter by lazy { RaceWeekendAdapter(this) }

    private var _binding: FragmentRaceDetailBinding? = null
    private val binding get() = _binding!!

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

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            raceDetailViewModel.raceInfo.collect { raceDetail ->
                raceDetail?.let {
                    binding.apply {
                        nameRace.text = it.competition
                        circuitRace.text = it.circuit
                        lapsRace.text = it.laps
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            raceDetailViewModel.raceList.collect { raceList ->
                raceList?.let { adapter.setItems(ArrayList(it)) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            raceDetailViewModel.addToCalendarEvent.collect { event ->
                event?.let { calendarHelper.addToCalendar(requireContext(), it) }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            raceDetailViewModel.errorMessage.collect { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    raceDetailViewModel.clearErrorMessage()
                }
            }
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
